
package test

import Chisel._

class GCD extends Module {
  val io = new Bundle {
    val a  = UInt(INPUT, 32)
    val b  = UInt(INPUT, 32)
    val e  = Bool(INPUT)
    val z  = UInt(OUTPUT, 32)
    val v  = Bool(OUTPUT)
  }
  val x = Reg(UInt(width = 32))
  val y = Reg(UInt(width = 32))
  when (x > y)   { x := x -% y }
  .otherwise     { y := y -% x }
  when (io.e) { x := io.a; y := io.b }
  io.z := x
  io.v := y === UInt(0)
}


class DecoupledGCD extends Module {
  val io = new Bundle {
    val in = Decoupled(new Bundle {
      val a = UInt(width = 32)
      val b = UInt(width = 32)
    }).flip
    val out = Decoupled(UInt(width = 32))
  }

  // Control State
  val busy = Reg(init = Bool(false))
  val done = Reg(init = Bool(false))
  // Data State
  val x = Reg(UInt(width = 32))
  val y = Reg(UInt(width = 32))

  // Control Logic
  io.in.ready := !busy
  io.out.valid := done
  when (busy & y === UInt(0)) {
    done := Bool(true)
  }
  when (done && io.out.ready) {
    busy := Bool(false)
  }
  val start = io.in.valid && io.in.ready
  when (start) {
    busy := Bool(true)
    done := Bool(false)
  }

  // Data Logic
  when (x > y)   { x := x -% y }
  .otherwise     { y := y -% x }
  when (start) { x := io.in.bits.a; y := io.in.bits.b }
  io.out.bits := x

}


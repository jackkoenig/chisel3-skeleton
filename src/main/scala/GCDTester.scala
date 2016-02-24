// See LICENSE for license details.

package test

import Chisel._
import Chisel.testers.BasicTester

import scala.util.Random

class GCDTester(numTests: Int) extends BasicTester {
  def gcd(a: Int, b: Int): Int = if(b == 0) a else gcd(b, a%b)
  def rand(): Int = Random.nextInt(100) + 1

  val dut = Module(new DecoupledGCD)

  val count = Reg(init = UInt(numTests-1, width = log2Up(numTests)))
  //val aGen = Seq.fill(numTests)(rand)
  //val bGen = Seq.fill(numTests)(rand)
  val aGen = Seq(11, 50, 28, 15)
  val bGen = Seq(4, 75, 64, 35)
  val a = Vec(aGen.map(x => UInt(x)))
  val b = Vec(bGen.map(x => UInt(x)))
  val z = Vec.tabulate(numTests)(i => UInt(gcd(aGen(i), bGen(i))))

  dut.io.out.ready := Bool(false)

  val en = Reg(init = Bool(true))
  dut.io.in.bits.a := a(count)
  dut.io.in.bits.b := b(count)
  dut.io.in.valid := en

  when(en && dut.io.in.ready) {
    en := Bool(false)
  }

  when(dut.io.out.valid && !en) {
    dut.io.out.ready := Bool(true)
    assert( dut.io.out.bits === z(count) )
    when( count === UInt(0) ) {
      stop()
    } .otherwise {
      en := Bool(true)
      count := count - UInt(1)
    }
  }

}


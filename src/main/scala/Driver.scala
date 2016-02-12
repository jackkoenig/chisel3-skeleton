
package test

import Chisel._
import firrtl._
import java.io._


object Driver extends App {
  val dut = "GCDTester"
  val buildDir = "./build"
  val verilogFile = s"${buildDir}/${dut}.v"
  val cppHarness = "./chisel/src/main/resources/top.cpp"

  // Run Chisel 3
  val s = Chisel.Driver.emit(() => new GCDTester(4))
  println(s)

  // Parse circuit into FIRRTL
  val circuit = firrtl.Parser.parse(s.split("\n"))

  val writer = new PrintWriter(new File(verilogFile))
  // Compile to verilog
  firrtl.VerilogCompiler.run(circuit, writer)

  // Build executable
  println("Running Chisel.Driver.verilogToCpp")
  Chisel.Driver.verilogToCpp(dut, new File(buildDir), Seq(), new File(cppHarness)).!
}

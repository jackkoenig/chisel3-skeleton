import sbt._
import Keys._
import complete._
import complete.DefaultParsers._

object BuildSettings extends Build {

  override lazy val settings = super.settings ++ Seq(
    organization := "berkeley",
    version      := "0.1",
    scalaVersion := "2.11.7",
    parallelExecution in Global := false,
    traceLevel   := 15,
    scalacOptions ++= Seq("-deprecation","-unchecked"),
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % "1.12.4" % "test",
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    )
  )

  lazy val firrtl = Project("firrtl", file("firrtl"))
  lazy val chisel = Project("chisel", file("chisel3")).dependsOn(firrtl)
  lazy val model = Project("chisel3-skeleton", file(".")).dependsOn(chisel, firrtl)

}

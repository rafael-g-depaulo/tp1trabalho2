name := "ED lib"
version := "1.0"
scalaVersion := "2.12.3"
parallelExecution in Test := false

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"


// scalacOptions ++= Seq("-feature")
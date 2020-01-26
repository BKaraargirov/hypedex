scalaVersion := "2.12.10"

lazy val global = project
  .in(file("."))
  .aggregate(
    hypedex,
    airquality
  )

lazy val hypedex = project
  .settings(
    settings,
    assemblySettings,
    libraryDependencies ++= Seq(
      dependencies.spark % "provided",
      dependencies.antlr,
      dependencies.jsqlparser,

      // Test depts
      dependencies.scalactic,
      dependencies.scalatest,
    )
  )

lazy val airquality = project
  .settings(
    settings,
    assemblySettings,
    libraryDependencies ++= Seq(
      dependencies.spark
    )
  ).dependsOn(hypedex)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case _                             => MergeStrategy.first
  }
)

lazy val dependencies =
  new {
    val spark = "org.apache.spark" %% "spark-sql" % "2.4.4"
    val antlr = "org.antlr" % "antlr4-runtime" % "4.7.2"
    val scalactic = "org.scalactic" %% "scalactic" % "3.1.0"
    val scalatest = "org.scalatest" %% "scalatest" % "3.1.0" % "test"
    val jsqlparser = "com.github.jsqlparser" % "jsqlparser" % "3.0"
  }

lazy val settings = Seq(
  scalacOptions ++=  Seq(
    "-unchecked",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-deprecation",
    "-encoding",
    "utf8"
  ),

  testOptions in Test += Tests.Argument("-oD")
)



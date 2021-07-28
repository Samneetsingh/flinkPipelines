name := "flinkPipelines"

version := "0.1"

scalaVersion := "2.12.11"
val flinkVersion = "1.13.1"

resolvers ++= Seq(
  "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
  Resolver.mavenLocal
)

// https://mvnrepository.com/artifact/org.apache.flink/flink-scala
libraryDependencies ++= Seq(
  "org.apache.flink" %% "flink-clients" % flinkVersion,
  "org.apache.flink" %% "flink-scala" % flinkVersion,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion,

  "org.apache.flink" %% "flink-connector-kafka" % flinkVersion,
  "org.apache.flink" % "flink-connector-base" % flinkVersion
)

assemblyJarName in assembly := s"kafka-pipeline.jar"

lazy val app = (project in file("src.main.scala"))
  .settings(
    assembly / mainClass := Some("pipeline.WordCount"),
    // more settings here ...
  )

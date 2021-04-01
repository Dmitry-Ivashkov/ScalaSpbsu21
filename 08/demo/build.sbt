version in Global:= "0.1"

//scalaVersion := "2.13.5"
scalaVersion in Global := "2.13.5"

/*
  root project
  + aggregation
 */

lazy val root = project.in(file("."))
  .settings(
    name := "lecture08",
//    run / aggregate := true
  ).aggregate(app)

/*
  Library project
  - cross building
  - publishing
  - unit tests
  - license
 */

lazy val library = project.in(file("library"))
  .settings(
    crossScalaVersions ++= Seq("2.12.12")
)

/* app project
  + set main class
  - assembly jar
  - integration tests
  - source generation
  - release notes generation task
 */

lazy val app = project.in(file("app"))
  .dependsOn(library)
  .settings(
    Compile / run / mainClass := Some("org.myApp.MyMainApp"),
//    mainClass := Some("org.myApp.MyMainApp")
  )
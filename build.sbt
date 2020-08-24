lazy val `scala-courses` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning, JavaAppPackaging, AshScriptPlugin)
    .settings(name := "Scala Courses")
    .settings(settings)
    .settings(dockerSettings)
    .settings(
      libraryDependencies ++= Seq(
          library.log4j2Api,
          library.log4j2Core,
          library.log4j2Scala,
          library.scalaLogging,
          library.slf4j,
          library.scalaCheck % Test,
          library.specs2 % Test,
          library.specs2Mock % Test
        ),
      version in Docker := "0.1.0-SNAPSHOT"
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {

    object Version {
      val log4j2 = "2.13.2"
      val log4j2Scala = "12.0"
      val scalaLogging = "3.9.2"
      val slf4j = "2.13.2"
      val scalaCheck = "3.1.2.0"
      val specs2 = "4.8.1"
    }

    val log4j2Api = "org.apache.logging.log4j" % "log4j-api" % Version.log4j2
    val log4j2Core = "org.apache.logging.log4j" % "log4j-core" % Version.log4j2 % Runtime
    val log4j2Scala = "org.apache.logging.log4j" % "log4j-api-scala_2.12" % Version.log4j2Scala
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging
    val slf4j = "org.apache.logging.log4j" % "log4j-slf4j-impl" % Version.slf4j

    //val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val scalaCheck = "org.scalatestplus" %% "scalacheck-1-14" % Version.scalaCheck
    val specs2 = "org.specs2" %% "specs2-core" % Version.specs2
    val specs2Mock = "org.specs2" %% "specs2-mock" % Version.specs2
  }


lazy val compilerPlugins =
  new {
    val kindProjector = "org.typelevel"     %% "kind-projector"  % "0.10.3" cross CrossVersion.binary
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  gitSettings ++
  scalafmtSettings

lazy val commonSettings =
  Seq(
    // scalaVersion from .travis.yml via sbt-travisci
    // scalaVersion := "2.12.9",
    version := "0.1.0-SNAPSHOT",
    organization := "com.codingmaniacs.scala.courses",
    headerLicense := Some(HeaderLicense.MIT("2019", "Geektimus")),
    scalacOptions ++= Seq(
        "-deprecation",
        "-encoding",
        "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-target:jvm-1.8",
        "-unchecked",
        "-Xfatal-warnings",
        "-Xlint",
//        "-Yno-adapted-args",
//        "-Ypartial-unification",
        "-Ywarn-dead-code",
//        "-Ywarn-infer-any",
        "-Ywarn-numeric-widen",
        "-Ywarn-unused",
//        "-Ywarn-unused-import",
        "-Ywarn-value-discard"
      ),
    parallelExecution.in(Test) := false,
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),
    addCompilerPlugin(compilerPlugins.kindProjector)
  )

lazy val dockerSettings =
  Seq(
    dockerBaseImage := "openjdk:8-jdk-alpine",
    dockerUpdateLatest := true
  )

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )

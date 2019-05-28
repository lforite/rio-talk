val Http4sVersion = "0.20.1"
val CirceVersion = "0.11.1"
val ScalaTestVersion = "3.0.5"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings()
  .aggregate(
    `http4s`,
    `slides`
  )

lazy val http4s = (project in file("http4s-example"))
  .settings(
    organization := "lforite",
    name := "http4s-example",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "org.scalatest"   %% "scalatest"           % ScalaTestVersion % Test,
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4")
  )



lazy val slides = project
  .in(file("slides"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "slides",
    libraryDependencies ++= Seq(
      "com.github.japgolly.scalajs-react" %%% "core" % "1.2.3",
      "org.scala-js" %%% "scalajs-dom" % "0.9.2"
    ),
    jsDependencies ++= Seq(
      "org.webjars.bower" % "react" % "15.2.1" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
      "org.webjars.bower" % "react" % "15.2.1" / "react-dom.js"         minified "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM"
    ),

    scalaJSUseMainModuleInitializer := true
  )

lazy val compileSlides = taskKey[Unit]("Compile slides")
compileSlides := {
  (compile in (slides, Compile)).value
  (fastOptJS in (slides, Compile)).value
  (fullOptJS in (slides, Compile)).value
}

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)

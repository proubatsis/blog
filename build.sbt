import com.typesafe.sbt.packager.docker._

val BlogScalaVersion = "2.12.6"
val BlogOrganization = "ca.panagiotis.blog"
val BlogVersion = "1.0"

scalaVersion := BlogScalaVersion

name := "personal-blog"
organization := BlogOrganization
version := BlogVersion

val Http4sVersion = "0.18.19"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"
val CirceVersion = "0.10.1"

def buildDockerCommands(name : String) = Seq(
  Cmd("FROM", "openjdk:8-jre-alpine"),
  Cmd("WORKDIR", "/opt/docker"),
  Cmd("ADD", "opt", "/opt"),
  ExecCmd("RUN", "apk", "add", "--no-cache", "bash"),
  ExecCmd("RUN", "chown", "-R", "daemon:daemon", "."),
  Cmd("USER", "daemon"),
  ExecCmd("ENTRYPOINT", s"/opt/docker/bin/$name")
)

val searchApiName = "blog-search-api"
lazy val searchApi = (project in file(searchApiName))
  .settings(
    name := searchApiName,
    version := BlogVersion,
    scalaVersion := BlogScalaVersion,
    dockerCommands := buildDockerCommands(searchApiName),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "io.circe" %% "circe-literal" % CirceVersion
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4"),
  )
  .enablePlugins(DockerPlugin, JavaServerAppPackaging)

val commentsApiName = "blog-comments-api"
lazy val commentsApi = (project in file(commentsApiName))
  .settings(
    name := commentsApiName,
    version := BlogVersion,
    scalaVersion := BlogScalaVersion,
    dockerCommands := buildDockerCommands(commentsApiName),
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "org.specs2"     %% "specs2-core"          % Specs2Version % "test",
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector"     % "0.9.6"),
    addCompilerPlugin("com.olegpy"     %% "better-monadic-for" % "0.2.4"),
  )
  .enablePlugins(DockerPlugin, JavaServerAppPackaging)


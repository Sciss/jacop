// build file for sbt, replaces original pom.xml maven build file.

name              := "jacop"

version           := "3.3.0"  // our own version

organization      := "de.sciss"  // we publish under this group ID

description       := "Java Constraint Programming solver (fork)"

homepage          := Some(url("https://github.com/Sciss/" + name.value))

licenses          := Seq("AGPL v3" -> url("https://www.gnu.org/licenses/agpl-3.0.txt"))

scalaVersion      := "2.10.3"

autoScalaLibrary  := false

crossPaths        := false

// as long as I don't know how to emulate the maven javacc / jjtree plugin,
// disable the compilation of the FlatZinc front-end.
//
// also, since we are going to develop our own Scala front-end,
// remove Scala sources
unmanagedSources in Compile := {
  val js   = (javaSource in Compile).value
  val base = js / "org" / "jacop"
  val ex1  = (base / "fz") ** "*.java"
  val ex2  = (base / "examples" / "RunExample.java")
  val ss   = (scalaSource in Compile).value
  val ex3  = ss ** "*.scala"
  val excl = ex1 +++ ex2 +++ ex3
  val prev = (unmanagedSources in Compile).value
  val next = prev --- excl
  next.get
}

// ---- publishing ----

publishMavenStyle := true

publishTo :=
  Some(if (version.value endsWith "-SNAPSHOT")
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  else
    "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
  )

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := { val n = name.value
<scm>
  <url>git@github.com:Sciss/{n}.git</url>
  <connection>scm:git:git@github.com:Sciss/{n}.git</connection>
</scm>
<developers>
  <developer>
    <id>kris</id>
    <name>Krzysztof Kuchcinski</name>
    <email>krzysztof.kuchcinski@cs.lth.se</email>
    <roles>
      <role>Core developer</role>
    </roles>
    <organization>cs.lth.se</organization>
    <timezone>+1</timezone>
  </developer>
  <developer>
    <id>radek</id>
    <name>Radoslaw Szymanek</name>
    <email>radoslaw.szymanek@gmail.com</email>
    <roles>
      <role>Core developer</role>
    </roles>
    <organization>osolpro.eu</organization>
    <timezone>+1</timezone>
  </developer>
  <developer>
    <id>sciss</id>
    <name>Hanns Holger Rutz</name>
    <url>http://www.sciss.de</url>
  </developer>
</developers>
}


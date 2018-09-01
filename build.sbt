// build file for sbt, replaces original pom.xml maven build file.

name              := "jacop"
version           := "3.5.0"  // our own version; corresponds with original jacop version 4.6.0-SNAPSHOT
organization      := "de.sciss"        // we publish under this group ID
description       := "Java Constraint Programming solver (fork)"
homepage          := Some(url("https://github.com/Sciss/" + name.value))
licenses          := Seq("AGPL v3" -> url("https://www.gnu.org/licenses/agpl-3.0.txt"))
scalaVersion      := "2.12.6"    // not used by project
autoScalaLibrary  := false
crossPaths        := false
javacOptions      := Seq("-source", "1.8", "-target", "1.8")

// cannot use target for javadoc
javacOptions in doc := Seq("-source", "1.8")

// as long as I don't know how to emulate the maven javacc / jjtree plugin,
// disable the compilation of the FlatZinc front-end.
//
// also, since we are going to develop our own Scala front-end,
// remove Scala sources
unmanagedSources in Compile := {
  val js   = (javaSource in Compile).value
  val base = js / "org" / "jacop"
  val ex1  = (base / "fz") ** "*.java"
  val ex2  = base / "examples" / "RunExample.java"
  val ss   = (scalaSource in Compile).value
  val ex3  = ss ** "*.scala"
  val ex4  = base / "examples" / "flatzinc" ** "*.java"
  val ex5  = base / "examples" / "fd" / "DeBruijn.java"  // uses scalac
  val excl = ex1 +++ ex2 +++ ex3 +++ ex4 +++ ex5
  val prev = (unmanagedSources in Compile).value
  val next = prev --- excl
  next.get
}

unmanagedSources in Test := {
  val js   = (javaSource in Test).value
  val base = js / "org" / "jacop"
  val ex1  = base / "QueueForwardTest.java"
  val ex2  = base / "ExampleBasedTest.java"
  val ex3  = base ** "Minizinc*.java"
  val ex4  = base ** "Mizinc*.java"
  val ex5  = base / "MinTestSuite.java"
  val excl = ex1 +++ ex2 +++ ex3 +++ ex4 +++ ex5
  val prev = (unmanagedSources in Test).value
  val next = prev --- excl
  next.get
}

libraryDependencies ++= Seq(
  "junit"        % "junit"           % "4.12"    % Test,
  "com.novocode" % "junit-interface" % "0.11"    % Test,
  "org.mockito"  % "mockito-all"     % "1.10.19" % Test
)

// ---- publishing ----

publishMavenStyle := true

publishTo :=
  Some(if (isSnapshot.value)
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


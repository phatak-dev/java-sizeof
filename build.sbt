
name := "java-sizeof"

organization:="com.madhu"

version:="0.1"

crossScalaVersions := Seq("2.10.0","2.11.0")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"

pgpSecretRing := file("/home/madhu/scalapgpkeys/phatak-dev-privatekey.asc")

pgpPublicRing := file("/home/madhu/scalapgpkeys/phatak-dev-publickey.asc")

publishMavenStyle := true

publishArtifact in Test := false


publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (
  <url>https://github.com/phatak-dev/java-sizeof</url>
  <licenses>
    <license>
      <name>Apache 2.0</name>
      <url>https://github.com/phatak-dev/java-sizeof/blob/master/LICENSE.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:phatak-dev/java-sizeof.git</url>
    <connection>scm:git:git@github.com:phatak-dev/java-sizeof.git</connection>
  </scm>
  <developers>
    <developer>
      <id>phatak-dev</id>
      <name>Madhukara phatak</name>
      <url>http://www.madhukaraphatak.com</url>
    </developer>
  </developers>)


credentials += Credentials(Path.userHome / ".sbt/0.13/" / ".credentials")








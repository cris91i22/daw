name := "tpdaw"
 
version := "1.0" 
      
lazy val `tpdaw` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( ehcache , ws , specs2 % Test , guice , "org.reactivemongo" %% "reactivemongo" % "0.12.6")

resolvers ++= Seq(
  "webjars"    at "http://webjars.github.com/m2"
)

libraryDependencies ++= Seq(
  "org.webjars"               %% "webjars-play"       % "2.6.2",
  "org.webjars"               % "bootstrap"           % "3.3.7" exclude("org.webjars", "jquery"),
  "org.webjars"               % "jquery"              % "3.2.1"
)



unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      
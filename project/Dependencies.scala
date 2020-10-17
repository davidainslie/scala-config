import sbt._

object Dependencies {
  def apply(): Seq[ModuleID] = Seq(
    scalatest, scalaMock, scalacheck, scalatestplus,
    monix, cats, monocle, refined, shapeless,
    betterFiles
  ).flatten

  lazy val scalatest: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.2.2" % "test, it" withSources() withJavadoc()
  )

  lazy val scalaMock: Seq[ModuleID] = Seq(
    "org.scalamock" %% "scalamock" % "5.0.0" % "test, it" withSources() withJavadoc()
  )

  lazy val scalacheck: Seq[ModuleID] = Seq(
    "org.scalacheck" %% "scalacheck" % "1.14.3" % "test, it" withSources() withJavadoc()
  )

  lazy val scalatestplus: Seq[ModuleID] = Seq(
    "org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2" % "test, it" withSources() withJavadoc()
  )

  lazy val monix: Seq[ModuleID] = Seq(
    "io.monix" %% "monix" % "3.2.2" withSources() withJavadoc()
  )

  lazy val cats: Seq[ModuleID] = {
    val group = "org.typelevel"
    val version = "2.2.0"

    Seq(
      "cats-core", "cats-effect", "cats-free"
    ).map(group %% _ % version withSources() withJavadoc()) ++ Seq(
      "cats-laws", "cats-testkit"
    ).map(group %% _ % version % "test, it" withSources() withJavadoc())
  }

  lazy val monocle: Seq[ModuleID] = {
    val group = "com.github.julien-truffaut"
    val version = "2.1.0"

    Seq(
      "monocle-core", "monocle-macro", "monocle-generic"
    ).map(group %% _ % version withSources() withJavadoc()) ++ Seq(
      "monocle-law"
    ).map(group %% _ % version % "test, it" withSources() withJavadoc())
  }

  lazy val refined: Seq[ModuleID] = {
    val group = "eu.timepit"
    val version = "0.9.16"

    Seq(
      "refined", "refined-pureconfig", "refined-cats"
    ).map(group %% _ % version withSources() withJavadoc())
  }

  lazy val shapeless: Seq[ModuleID] = Seq(
    "com.chuusai" %% "shapeless" % "2.3.3"
  )

  lazy val betterFiles: Seq[ModuleID] = Seq(
    "com.github.pathikrit" %% "better-files" % "3.9.1" withSources() withJavadoc()
  )
}
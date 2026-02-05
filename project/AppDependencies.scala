import sbt.*

object AppDependencies {

  val bootstrapVersion = "10.5.0"
  val playSuffix = "-play-30"

  val compile: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% s"bootstrap-backend$playSuffix" % bootstrapVersion
  )

  val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% s"bootstrap-test$playSuffix" % bootstrapVersion
  ).map(_ % Test)
}

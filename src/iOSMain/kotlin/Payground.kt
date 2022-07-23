import Segment.*

fun test() {
    val config = SEGAnalyticsConfiguration.configurationWithWriteKey("myAPI")

    SEGAnalytics.setupWithConfiguration(config)
    SEGAnalytics.sharedAnalytics().track("event")

}
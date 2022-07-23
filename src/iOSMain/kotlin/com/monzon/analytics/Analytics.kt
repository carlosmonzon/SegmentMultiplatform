package com.monzon.analytics

import Segment.SEGAnalytics
import Segment.SEGAnalyticsConfiguration

actual class Analytics internal constructor(val ios: SEGAnalytics) {

    actual companion object {
        actual fun setupWithConfiguration(configuration: Configuration): Analytics {
            val analyticsConfig = SEGAnalyticsConfiguration.configurationWithWriteKey(configuration.writeKey)
            SEGAnalytics.setupWithConfiguration(analyticsConfig)
            return shared(null)
        }

        actual fun shared(context: Any?): Analytics =
            Analytics(SEGAnalytics.sharedAnalytics())
    }

    init {
    }

    actual fun alias(userId: String, options: Map<Any?, *>?) =
        ios.alias(userId, options?.let { mapOf("context" to it) })

    actual fun track(name: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) =
        ios.track(name, properties, options?.let { mapOf("context" to it) })

    actual fun identify(userId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) =
        ios.identify(userId, traits, options?.let { mapOf("context" to it) })

    actual fun screen(
        screenTitle: String,
        properties: Map<Any?, *>?,
        options: Map<Any?, *>?
    ) = ios.screen(screenTitle, properties, options?.let { mapOf("context" to it) })

    actual fun group(groupId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) =
        ios.group(groupId, traits, options?.let { mapOf("context" to it) })

    actual fun reset() {
        ios.reset()
    }
}
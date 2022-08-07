package com.monzon.analytics

import Segment.SEGAnalytics
import Segment.SEGAnalyticsConfiguration

internal class SegmentWrapperImpl(val ios: SEGAnalytics) : SegmentWrapper {

    companion object {
        fun shared() = SegmentWrapperImpl(SEGAnalytics.sharedAnalytics())
    }

    class Factory : SegmentWrapper.Factory {
        override fun create(configuration: Configuration, context: Any?): SegmentWrapper {
            val analyticsConfig = SEGAnalyticsConfiguration.configurationWithWriteKey(configuration.writeKey)
            SEGAnalytics.setupWithConfiguration(analyticsConfig)
            return SegmentWrapperImpl(SEGAnalytics.sharedAnalytics())
        }

    }

    override fun alias(userId: String, options: Map<Any?, *>?) =
        ios.alias(userId, options?.let { mapOf("context" to it) })

    override fun track(name: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) =
        ios.track(name, properties, options?.let { mapOf("context" to it) })

    override fun identify(userId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) =
        ios.identify(userId, traits, options?.let { mapOf("context" to it) })

    override fun screen(
        screenTitle: String,
        properties: Map<Any?, *>?,
        options: Map<Any?, *>?
    ) = ios.screen(screenTitle, properties, options?.let { mapOf("context" to it) })

    override fun group(groupId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) =
        ios.group(groupId, traits, options?.let { mapOf("context" to it) })

    override fun reset() {
        ios.reset()
    }
}
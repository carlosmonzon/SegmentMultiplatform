package com.monzon.analytics

import android.content.Context
import com.segment.analytics.Analytics
import com.segment.analytics.Options
import com.segment.analytics.Properties
import com.segment.analytics.Traits

internal class SegmentWrapperImpl(private val android: Analytics) : SegmentWrapper {

    companion object {
        fun shared(context: Context) = SegmentWrapperImpl(Analytics.with(context))
    }

    class Factory : SegmentWrapper.Factory {
        override fun create(configuration: Configuration, context: Any?): SegmentWrapper {
            val analyticsConfig = Analytics.Builder(context as Context, configuration.writeKey)
            val analytics = analyticsConfig.build()
            Analytics.setSingletonInstance(analytics)
            return SegmentWrapperImpl(analytics)
        }
    }

    override fun track(name: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) =
        android.track(
            name,
            Properties().apply {
                properties?.forEach { property ->
                    (property.key as? String)?.let { putValue(it, property.value) }
                }
            },
            Options().apply {
                options?.forEach { property ->
                    (property.key as? String)?.let { putContext(it, property.value) }
                }
            }
        )

    override fun identify(userId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) =
        android.alias(
            userId,
            Options().apply {
                options?.forEach { property ->
                    (property.key as? String)?.let { putContext(it, property.value) }
                }
            }
        )

    override fun alias(userId: String, options: Map<Any?, *>?) =
        android.alias(
            userId,
            Options().apply {
                options?.forEach { property ->
                    (property.key as? String)?.let { putContext(it, property.value) }
                }
            }
        )

    override fun screen(screenTitle: String, properties: Map<Any?, *>?, options: Map<Any?, *>?) =
        android.screen(
            null,
            screenTitle,
            Properties().apply {
                properties?.forEach { property ->
                    (property.key as? String)?.let { putValue(it, property.value) }
                }
            },
            Options().apply {
                options?.forEach { property ->
                    (property.key as? String)?.let { putContext(it, property.value) }
                }
            }
        )

    override fun group(groupId: String, traits: Map<Any?, *>?, options: Map<Any?, *>?) =
        android.group(
            groupId,
            Traits().apply
            {
                traits?.forEach { trait ->
                    (trait.key as? String)?.let { putValue(it, trait.value) }
                }
            },
            Options().apply
            {
                options?.forEach { property ->
                    (property.key as? String)?.let { putContext(it, property.value) }
                }
            }
        )

    override fun reset() = android.reset()

}
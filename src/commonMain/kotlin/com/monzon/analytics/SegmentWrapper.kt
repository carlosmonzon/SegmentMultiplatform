package com.monzon.analytics

import com.monzon.analytics.domain.Event

interface IAnalytics {

    /**
     * A factory that can produce [IAnalytics] instances.
     */
    interface Factory {
        /**
         * Creates a [IAnalytics] object associated with the provided [Configuration].
         */
        fun create(configuration: Configuration): IAnalytics
    }

    fun track(name: String, properties: Map<Any?, *>? = null, options: Map<Any?, *>? = null)
    fun identify(userId: String, traits: Map<Any?, *>? = null, options: Map<Any?, *>? = null)
    fun alias(userId: String, options: Map<Any?, *>? = null)
    fun screen(screenTitle: String, properties: Map<Any?, *>? = null, options: Map<Any?, *>? = null)
    fun group(groupId: String, traits: Map<Any?, *>? = null, options: Map<Any?, *>? = null)

    fun reset()
}
package com.monzon.analytics

internal interface SegmentWrapper {

    /**
     * A factory that can produce [SegmentWrapper] instances.
     */
    interface Factory {
        /**
         * Creates a [SegmentWrapper] object associated with the provided [Configuration].
         */
        fun create(configuration: Configuration, context: Any? = null): SegmentWrapper
    }

    fun track(name: String, properties: Map<Any?, *>? = null, options: Map<Any?, *>? = null)
    fun identify(userId: String, traits: Map<Any?, *>? = null, options: Map<Any?, *>? = null)
    fun alias(userId: String, options: Map<Any?, *>? = null)
    fun screen(screenTitle: String, properties: Map<Any?, *>? = null, options: Map<Any?, *>? = null)
    fun group(groupId: String, traits: Map<Any?, *>? = null, options: Map<Any?, *>? = null)

    fun reset()
}
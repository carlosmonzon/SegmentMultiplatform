package com.monzon.analytics

import com.monzon.analytics.domain.Event

interface Analytics {

    fun track(event: Event)
}

class AnalyticsImpl constructor(private val segmentWrapper: SegmentWrapper) : Analytics,
    SegmentWrapper by segmentWrapper {
    companion object {
        fun shared(analytics: SegmentWrapper) = AnalyticsImpl(analytics)
    }

    override fun track(event: Event) {
        segmentWrapper.track(event.name, event.toMap())
    }
}
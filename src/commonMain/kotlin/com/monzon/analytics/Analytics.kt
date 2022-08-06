package com.monzon.analytics

import com.monzon.analytics.domain.Event


interface EventAnalytics {

    fun track(event: Event)
}

public expect fun EventAnalytics(configuration: Configuration): EventAnalytics
public expect fun EventAnalytics(): EventAnalytics

class Analytics constructor(private val analytics: IAnalytics) : EventAnalytics, IAnalytics by analytics {

    companion object {
        fun shared(analytics: IAnalytics) = Analytics(analytics)
    }

    override fun track(event: Event) {
        analytics.track(event.name, event.toMap())
    }
}
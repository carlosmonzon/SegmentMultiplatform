package com.monzon.analytics

import com.monzon.analytics.domain.Event


interface EventAnalytics {
    companion object

    fun track(event: Event)
}

public expect fun EventAnalytics(configuration: Configuration): EventAnalytics

class Analytics constructor(private val analytics: IAnalytics) : EventAnalytics, IAnalytics by analytics {
    override fun track(event: Event) {
        analytics.track(event.name, event.toMap())
    }
}
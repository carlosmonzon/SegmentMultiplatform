package com.monzon.analytics

public actual fun EventAnalytics(configuration: Configuration): EventAnalytics =
    Analytics(analytics = AnalyticsImpl.Factory().create(configuration))


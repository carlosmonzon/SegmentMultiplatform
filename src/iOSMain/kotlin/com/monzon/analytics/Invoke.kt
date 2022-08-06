package com.monzon.analytics

public actual fun Analytics(configuration: Configuration): Analytics =
    AnalyticsImpl(segmentWrapper = SegmentWrapperImpl.Factory().create(configuration))

public actual fun Analytics(): Analytics = AnalyticsImpl.Companion.shared(SegmentWrapperImpl.shared())





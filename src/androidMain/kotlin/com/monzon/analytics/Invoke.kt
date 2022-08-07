package com.monzon.analytics

import android.content.Context
import androidx.startup.Initializer

private var appContext: Context? = null
actual fun Analytics(configuration: Configuration): Analytics {
    val context = appContext!!
    return AnalyticsImpl(segmentWrapper = SegmentWrapperImpl.Factory().create(configuration, context))
}

actual fun Analytics(): Analytics {
    val context = appContext!!
    return AnalyticsImpl.Companion.shared(SegmentWrapperImpl.shared(context))
}


//using androidx startup to get app context
internal class AnalyticsInitializer : Initializer<Context> {
    override fun create(context: Context): Context = context.applicationContext.also { appContext = it }
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

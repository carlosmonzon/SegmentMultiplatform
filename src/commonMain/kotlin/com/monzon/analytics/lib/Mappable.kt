package com.monzon.analytics.lib

interface Mappable {
    fun toMap(): Map<Any?, *>?
}
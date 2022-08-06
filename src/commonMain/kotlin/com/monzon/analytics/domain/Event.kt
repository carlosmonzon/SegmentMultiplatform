package com.monzon.analytics.domain

sealed class Event {
    abstract val name: String
    abstract fun toMap():  Map<Any?, *>? // can this be auto generated? ie: kotlin-serialization? reflection?
}
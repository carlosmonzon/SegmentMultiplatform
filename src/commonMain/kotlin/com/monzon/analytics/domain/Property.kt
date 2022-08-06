package com.monzon.analytics.domain

sealed class Property {
    abstract fun toMap():  Map<Any?, *>?
}
package com.monzon.analytics.lib

interface Named {
    val name: String
}

interface EventTyped {
    val type: EventType
}

interface Event : Named, Mappable, EventTyped


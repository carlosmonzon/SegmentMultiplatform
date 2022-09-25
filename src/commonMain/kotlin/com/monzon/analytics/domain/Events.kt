package com.monzon.analytics.domain

import com.monzon.analytics.lib.Event
import com.monzon.analytics.lib.EventType

data class AddProductEvent(val product: ProductProperty) : Event {
    override fun toMap(): Map<Any?, *>? {
        return mapOf("product" to product.toMap())
    }

    override val type: EventType = EventType.ADD_PRODUCT

    override val name = type.value
}
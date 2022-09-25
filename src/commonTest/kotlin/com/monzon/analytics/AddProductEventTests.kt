package com.monzon.analytics

import com.monzon.analytics.domain.AddProductEvent
import com.monzon.analytics.domain.ProductProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class AddProductEventTests {

    @Test
    fun serialisation() {
        val product = ProductProperty("1", 10.0)
        val event = AddProductEvent(product = product)
        val map = event.toMap()
        val expectedMap: Map<Any?, *> =
            mapOf("product" to mapOf("skuId" to "1", "price" to 10.0))
        assertEquals(expectedMap, map)

        assertEquals(event.name, "Product Added")
    }

}
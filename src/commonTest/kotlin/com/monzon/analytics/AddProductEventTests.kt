package com.monzon.analytics

import com.monzon.analytics.domain.AddProductEvent
import com.monzon.analytics.domain.ProductProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class AddProductEventTests {

    @Test
    fun serialisation() {
        val product = ProductProperty("1", 10.0)
        val event = AddProductEvent(name = "Add product", product = product)
        val map = event.toMap()
        val expectedMap = mapOf("name" to "Add product", "product" to mapOf("skuId" to "1", "price" to 10.0))
        assertEquals(expectedMap, map)
    }

}
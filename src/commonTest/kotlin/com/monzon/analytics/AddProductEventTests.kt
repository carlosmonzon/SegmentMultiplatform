package com.monzon.analytics

import com.monzon.analytics.domain.AddProductEvent
import com.monzon.analytics.domain.Denomination
import com.monzon.analytics.domain.ProductProperty
import kotlin.test.Test
import kotlin.test.assertEquals

class AddProductEventTests {

    @Test
    fun serialisation() {
        val product = ProductProperty("1", Denomination("denominationId", 10.00))
        val event = AddProductEvent(product = product)
        val map = event.toMap()
        val denominationMap = mapOf("id" to "denominationId", "value" to 10.0)
        val expectedMap: Map<Any?, *> =
            mapOf("product" to mapOf("skuId" to "1", "denomination" to denominationMap))
        assertEquals(expectedMap, map)

        assertEquals(event.name, "Product Added")
    }

}
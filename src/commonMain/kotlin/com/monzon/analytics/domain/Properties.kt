package com.monzon.analytics.domain

import com.monzon.analytics.lib.Mappable

data class ProductProperty(val skuId: String, val price: Double) : Mappable {
    override fun toMap(): Map<Any?, *>? {
        return mapOf("skuId" to skuId, "price" to price)
    }
}
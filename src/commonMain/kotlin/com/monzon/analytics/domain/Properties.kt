package com.monzon.analytics.domain

data class ProductProperty(val skuId: String, val price: Double) : Property() {
    override fun toMap(): Map<Any?, *>? {
        return mapOf("skuId" to skuId, "price" to price)
    }
}
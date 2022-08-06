package com.monzon.analytics.domain


data class AddProductEvent(override val name: String, val product: ProductProperty) : Event() {
    override fun toMap():  Map<Any?, *>? {
        return mapOf("name" to name, "product" to product.toMap())
    }
}
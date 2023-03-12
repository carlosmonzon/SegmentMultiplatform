package com.monzon.analytics.domain

import org.monzon.annotation.Property

@Property
data class ProductProperty(val skuId: String, val denomination: Denomination)

@Property
data class Denomination(val id: String, val value: Double)
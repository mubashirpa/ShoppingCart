package com.evaluation.shoppingcart.data.remote.dto.shoppingItems

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingItemDto(
    val itemID: String? = null,
    val itemName: String? = null,
    val sellingPrice: Double? = null,
    val taxPercentage: Double? = null,
)

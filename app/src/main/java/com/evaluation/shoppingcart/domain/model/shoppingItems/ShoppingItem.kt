package com.evaluation.shoppingcart.domain.model.shoppingItems

data class ShoppingItem(
    val itemID: String? = null,
    val itemName: String? = null,
    val sellingPrice: Double? = null,
    val taxPercentage: Double? = null,
    val quantity: Int? = null,
)

package com.evaluation.shoppingcart.presentation.cart

import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem

data class CartUiState(
    val cartItems: List<ShoppingItem> = emptyList(),
    val loading: Boolean = false,
    val subTotal: Double = 0.0,
    val taxTotal: Double = 0.0,
    val total: Double = 0.0,
)

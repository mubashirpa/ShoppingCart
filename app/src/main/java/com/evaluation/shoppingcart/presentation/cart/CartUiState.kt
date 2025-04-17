package com.evaluation.shoppingcart.presentation.cart

import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem

data class CartUiState(
    val cartItems: List<ShoppingItem> = emptyList(),
)

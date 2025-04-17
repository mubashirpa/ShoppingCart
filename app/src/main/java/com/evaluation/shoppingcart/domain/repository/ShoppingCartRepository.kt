package com.evaluation.shoppingcart.domain.repository

import com.evaluation.shoppingcart.data.local.entity.ShoppingCartEntity

interface ShoppingCartRepository {
    suspend fun addToShoppingCart(item: ShoppingCartEntity)

    suspend fun updateQuantity(
        itemID: String,
        quantity: Int,
    )

    suspend fun isItemInCart(itemID: String): Boolean
}

package com.evaluation.shoppingcart.domain.repository

import com.evaluation.shoppingcart.data.local.entity.ShoppingCartEntity
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {
    fun getAllItems(): Flow<List<ShoppingCartEntity>>

    suspend fun addToShoppingCart(item: ShoppingCartEntity)

    suspend fun updateQuantity(
        itemID: String,
        quantity: Int,
    )

    suspend fun isItemInCart(itemID: String): Boolean

    fun getTotalItemCount(): Flow<Int?>
}

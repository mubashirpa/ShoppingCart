package com.evaluation.shoppingcart.domain.repository

import com.evaluation.shoppingcart.data.remote.dto.shoppingItems.ShoppingItemDto

interface ShoppingItemsRepository {
    suspend fun getShoppingItems(): List<ShoppingItemDto>
}

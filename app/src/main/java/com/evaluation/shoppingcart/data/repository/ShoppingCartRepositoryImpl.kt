package com.evaluation.shoppingcart.data.repository

import com.evaluation.shoppingcart.data.local.database.ShoppingCartDatabase
import com.evaluation.shoppingcart.data.local.entity.ShoppingCartEntity
import com.evaluation.shoppingcart.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow

class ShoppingCartRepositoryImpl(
    shoppingCartDatabase: ShoppingCartDatabase,
) : ShoppingCartRepository {
    private val cartItemDao = shoppingCartDatabase.shoppingCartDao()

    override fun getAllItems(): Flow<List<ShoppingCartEntity>> = cartItemDao.getAllItems()

    override suspend fun addToShoppingCart(item: ShoppingCartEntity) {
        cartItemDao.insertItem(item)
    }

    override suspend fun updateQuantity(
        itemID: String,
        quantity: Int,
    ) {
        cartItemDao.updateQuantity(itemID, quantity)
    }

    override suspend fun isItemInCart(itemID: String): Boolean = cartItemDao.isItemInCart(itemID)
}

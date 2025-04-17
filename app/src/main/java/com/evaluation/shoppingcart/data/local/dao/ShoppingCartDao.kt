package com.evaluation.shoppingcart.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.evaluation.shoppingcart.data.local.entity.ShoppingCartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {
    @Query("SELECT * FROM shopping_cart")
    fun getAllItems(): Flow<List<ShoppingCartEntity>>

    @Upsert
    suspend fun insertItem(cartItem: ShoppingCartEntity)

    @Query("UPDATE shopping_cart SET quantity = quantity + :quantity WHERE itemID = :itemID")
    suspend fun updateQuantity(
        itemID: String,
        quantity: Int,
    )

    @Query("SELECT EXISTS(SELECT * FROM shopping_cart WHERE itemID = :itemID)")
    suspend fun isItemInCart(itemID: String): Boolean
}

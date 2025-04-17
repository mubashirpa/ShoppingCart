package com.evaluation.shoppingcart.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_cart")
data class ShoppingCartEntity(
    @PrimaryKey val itemID: String,
    val itemName: String?,
    val sellingPrice: Double?,
    val taxPercentage: Double?,
    val quantity: Int?,
)

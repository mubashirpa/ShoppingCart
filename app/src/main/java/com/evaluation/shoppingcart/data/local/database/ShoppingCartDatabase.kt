package com.evaluation.shoppingcart.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evaluation.shoppingcart.data.local.dao.ShoppingCartDao
import com.evaluation.shoppingcart.data.local.entity.ShoppingCartEntity

@Database(
    entities = [ShoppingCartEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao(): ShoppingCartDao
}

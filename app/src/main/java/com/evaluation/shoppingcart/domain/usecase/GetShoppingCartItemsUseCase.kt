package com.evaluation.shoppingcart.domain.usecase

import com.evaluation.shoppingcart.data.mapper.toShoppingItem
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetShoppingCartItemsUseCase(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend operator fun invoke(): Flow<List<ShoppingItem>> =
        withContext(ioDispatcher) {
            shoppingCartRepository.getAllItems().map { it.map { item -> item.toShoppingItem() } }
        }
}

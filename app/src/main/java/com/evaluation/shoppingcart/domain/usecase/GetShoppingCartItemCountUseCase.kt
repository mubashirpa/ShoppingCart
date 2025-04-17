package com.evaluation.shoppingcart.domain.usecase

import com.evaluation.shoppingcart.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetShoppingCartItemCountUseCase(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend operator fun invoke(): Flow<Int> =
        withContext(ioDispatcher) {
            shoppingCartRepository.getTotalItemCount().map { it ?: 0 }
        }
}

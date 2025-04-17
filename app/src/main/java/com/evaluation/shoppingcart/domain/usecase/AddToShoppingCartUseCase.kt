package com.evaluation.shoppingcart.domain.usecase

import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.core.UiText
import com.evaluation.shoppingcart.data.mapper.toShoppingCartItem
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.domain.repository.ShoppingCartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class AddToShoppingCartUseCase(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    operator fun invoke(shoppingItem: ShoppingItem): Flow<Result<Boolean>> =
        flow {
            try {
                emit(Result.Loading())
                withContext(ioDispatcher) {
                    val isItemInCart = shoppingCartRepository.isItemInCart(shoppingItem.itemID!!)
                    if (isItemInCart) {
                        shoppingCartRepository.updateQuantity(shoppingItem.itemID, 1)
                    } else {
                        shoppingCartRepository.addToShoppingCart(shoppingItem.toShoppingCartItem())
                    }
                }
                emit(Result.Success(true))
            } catch (_: Exception) {
                emit(Result.Error(UiText.StringResource(R.string.error_unknown)))
            }
        }
}

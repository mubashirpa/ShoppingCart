package com.evaluation.shoppingcart.domain.usecase

import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.core.UiText
import com.evaluation.shoppingcart.core.utils.KtorException
import com.evaluation.shoppingcart.data.mapper.toShoppingItem
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.domain.repository.ShoppingItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.net.ConnectException

class GetShoppingItemsUseCase(
    private val shoppingItemsRepository: ShoppingItemsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    operator fun invoke(): Flow<Result<List<ShoppingItem>>> =
        flow {
            try {
                emit(Result.Loading())
                val items =
                    withContext(ioDispatcher) {
                        shoppingItemsRepository.getShoppingItems().map { it.toShoppingItem() }
                    }
                emit(Result.Success(items))
            } catch (_: ConnectException) {
                emit(Result.Error(UiText.StringResource(R.string.error_connect)))
            } catch (e: KtorException) {
                emit(Result.Error(e.localizedMessage))
            } catch (_: Exception) {
                emit(Result.Error(UiText.StringResource(R.string.error_unknown)))
            }
        }
}

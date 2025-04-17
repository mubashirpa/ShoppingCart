package com.evaluation.shoppingcart.data.repository

import com.evaluation.shoppingcart.core.Constants
import com.evaluation.shoppingcart.core.utils.toResult
import com.evaluation.shoppingcart.data.remote.dto.shoppingItems.ShoppingItemDto
import com.evaluation.shoppingcart.domain.repository.ShoppingItemsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class ShoppingItemsRepositoryImpl(
    private val httpClient: HttpClient,
) : ShoppingItemsRepository {
    override suspend fun getShoppingItems(): List<ShoppingItemDto> = httpClient.get(Constants.API_BASE_URL).toResult()
}

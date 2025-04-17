package com.evaluation.shoppingcart.presentation.home

import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.core.UiText
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem

data class HomeUiState(
    val isRefreshing: Boolean = false,
    val openProgressDialog: Boolean = false,
    val shoppingItemsResult: Result<List<ShoppingItem>> = Result.Empty(),
    val userMessage: UiText? = null,
)

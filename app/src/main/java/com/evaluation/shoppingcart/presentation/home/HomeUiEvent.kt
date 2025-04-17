package com.evaluation.shoppingcart.presentation.home

import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem

sealed class HomeUiEvent {
    data class AddToCart(
        val item: ShoppingItem,
        val addedToCart: Boolean,
    ) : HomeUiEvent()

    data object Refresh : HomeUiEvent()

    data object Retry : HomeUiEvent()

    data object UserMessageShown : HomeUiEvent()
}

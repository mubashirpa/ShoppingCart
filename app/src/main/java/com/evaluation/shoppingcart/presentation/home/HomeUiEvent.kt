package com.evaluation.shoppingcart.presentation.home

sealed class HomeUiEvent {
    data object Refresh : HomeUiEvent()

    data object Retry : HomeUiEvent()
}

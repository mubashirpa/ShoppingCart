package com.evaluation.shoppingcart.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.shoppingcart.domain.usecase.GetShoppingCartItemsUseCase
import kotlinx.coroutines.launch

class CartViewModel(
    private val getShoppingCartItemsUseCase: GetShoppingCartItemsUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(CartUiState())
        private set

    init {
        getShoppingCartItems()
    }

    private fun getShoppingCartItems() {
        viewModelScope.launch {
            getShoppingCartItemsUseCase().collect {
                uiState = uiState.copy(cartItems = it)
            }
        }
    }
}

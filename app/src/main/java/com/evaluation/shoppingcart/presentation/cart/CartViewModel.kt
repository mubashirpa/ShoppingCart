package com.evaluation.shoppingcart.presentation.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.domain.usecase.GetShoppingCartItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat

class CartViewModel(
    private val getShoppingCartItemsUseCase: GetShoppingCartItemsUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(CartUiState())
        private set

    init {
        uiState = uiState.copy(loading = true)
        getShoppingCartItems()
    }

    private fun getShoppingCartItems() {
        viewModelScope.launch {
            getShoppingCartItemsUseCase().collect { items ->
                val (subTotal, taxTotal) =
                    withContext(Dispatchers.Default) {
                        calculateCartSummary(items)
                    }
                val total = roundOffDecimal(subTotal + taxTotal)

                uiState =
                    uiState.copy(
                        cartItems = items,
                        loading = false,
                        subTotal = subTotal,
                        taxTotal = taxTotal,
                        total = total,
                    )
            }
        }
    }

    private fun calculateCartSummary(items: List<ShoppingItem>): Pair<Double, Double> {
        var subTotal = 0.0
        var taxTotal = 0.0

        for (item in items) {
            val price = item.sellingPrice ?: 0.0
            val qty = item.quantity ?: 0
            val taxPercent = item.taxPercentage ?: 0.0

            val itemTotal = price * qty
            subTotal += itemTotal
            taxTotal += itemTotal * taxPercent / 100
        }

        return roundOffDecimal(subTotal) to roundOffDecimal(taxTotal)
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }
}

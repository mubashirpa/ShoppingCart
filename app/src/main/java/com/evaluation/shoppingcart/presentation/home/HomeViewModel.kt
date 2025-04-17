package com.evaluation.shoppingcart.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.domain.usecase.AddToShoppingCartUseCase
import com.evaluation.shoppingcart.domain.usecase.GetShoppingItemsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val addToShoppingCartUseCase: AddToShoppingCartUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    private var getShoppingItemsJob: Job? = null

    init {
        getShoppingItems(false)
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.AddToCart -> {
                addToShoppingCart(event.item)
            }

            HomeUiEvent.Refresh -> {
                getShoppingItems(true)
            }

            HomeUiEvent.Retry -> {
                getShoppingItems(false)
            }
        }
    }

    private fun getShoppingItems(isRefreshing: Boolean) {
        // Cancel any ongoing getShoppingItemsJob before making a new call.
        getShoppingItemsJob?.cancel()
        getShoppingItemsJob =
            getShoppingItemsUseCase()
                .onEach { result ->
                    uiState = uiState.copy(shoppingItemsResult = result)
                    when (result) {
                        is Result.Empty -> {}

                        is Result.Error -> {
                            // The Result.Error state is only used during initial loading and retry attempts.
                            // Otherwise, a snackbar is displayed using the userMessage property.
                            uiState =
                                if (isRefreshing) {
                                    uiState.copy(
                                        isRefreshing = false,
                                        userMessage = result.message,
                                    )
                                } else {
                                    uiState.copy(shoppingItemsResult = result)
                                }
                        }

                        is Result.Loading -> {
                            // The Result.Loading state is only used during initial loading and retry attempts.
                            // In other cases, the PullRefreshIndicator is shown with isRefreshing = true.
                            uiState =
                                if (isRefreshing) {
                                    uiState.copy(isRefreshing = true)
                                } else {
                                    uiState.copy(shoppingItemsResult = result)
                                }
                        }

                        is Result.Success -> {
                            uiState =
                                uiState.copy(
                                    isRefreshing = false,
                                    shoppingItemsResult = result,
                                )
                        }
                    }
                }.launchIn(viewModelScope)
    }

    private fun addToShoppingCart(shoppingItem: ShoppingItem) {
        addToShoppingCartUseCase(shoppingItem)
            .onEach { result ->
                when (result) {
                    is Result.Empty -> {}

                    is Result.Error -> {
                        uiState =
                            uiState.copy(
                                openProgressDialog = false,
                                userMessage = result.message,
                            )
                    }

                    is Result.Loading -> {
                        uiState = uiState.copy(openProgressDialog = true)
                    }

                    is Result.Success -> {
                        uiState = uiState.copy(openProgressDialog = false)
                    }
                }
            }.launchIn(viewModelScope)
    }
}

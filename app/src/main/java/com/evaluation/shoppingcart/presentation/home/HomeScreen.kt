package com.evaluation.shoppingcart.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.presentation.components.ErrorScreen
import com.evaluation.shoppingcart.presentation.components.LoadingScreen

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        when (val shoppingItemsResult = uiState.shoppingItemsResult) {
            is Result.Empty -> {}

            is Result.Error -> {
                ErrorScreen(
                    onRetryClick = {
                        onEvent(HomeUiEvent.Retry)
                    },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    errorMessage = shoppingItemsResult.message!!.asString(),
                )
            }

            is Result.Loading -> {
                LoadingScreen(modifier = Modifier.padding(innerPadding))
            }

            is Result.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
                ) {
                    val shoppingItems = shoppingItemsResult.data.orEmpty()
                    items(
                        items = shoppingItems,
                        key = { it.itemID!! },
                    ) { item ->
                        ListItem(
                            headlineContent = {
                                Text(text = item.itemName.orEmpty())
                            },
                        )
                    }
                }
            }
        }
    }
}

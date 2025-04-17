package com.evaluation.shoppingcart.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.presentation.components.ErrorScreen
import com.evaluation.shoppingcart.presentation.components.LoadingScreen
import com.evaluation.shoppingcart.presentation.home.components.ShoppingListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                        ShoppingListItem(
                            item = item,
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            }
        }
    }
}

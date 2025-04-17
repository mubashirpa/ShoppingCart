package com.evaluation.shoppingcart.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.core.Result
import com.evaluation.shoppingcart.presentation.components.ErrorScreen
import com.evaluation.shoppingcart.presentation.components.LoadingScreen
import com.evaluation.shoppingcart.presentation.components.ProgressDialog
import com.evaluation.shoppingcart.presentation.home.components.ShoppingListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onNavigateToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    uiState.userMessage?.let { userMessage ->
        LaunchedEffect(userMessage) {
            snackbarHostState.showSnackbar(userMessage.asString(context))
            // Once the message is displayed and dismissed, notify the ViewModel.
            onEvent(HomeUiEvent.UserMessageShown)
        }
    }

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
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToCart,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null,
                    )
                },
                text = {
                    val text =
                        if (uiState.shoppingCartItemCount > 0) {
                            stringResource(
                                id = R.string.cart_,
                                uiState.shoppingCartItemCount.toString(),
                            )
                        } else {
                            stringResource(id = R.string.cart)
                        }
                    Text(text = text)
                },
            )
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
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = {
                        onEvent(HomeUiEvent.Refresh)
                    },
                    modifier = Modifier.padding(innerPadding),
                    state = rememberPullToRefreshState(),
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        val shoppingItems = shoppingItemsResult.data.orEmpty()
                        items(
                            items = shoppingItems,
                            key = { it.itemID!! },
                        ) { item ->
                            ShoppingListItem(
                                onClick = {},
                                item = item,
                                onAddToCartClick = {
                                    onEvent(HomeUiEvent.AddToCart(item))
                                },
                                modifier = Modifier.animateItem(),
                            )
                        }
                    }
                }
            }
        }
    }

    ProgressDialog(
        open = uiState.openProgressDialog,
        onDismissRequest = {},
    )
}

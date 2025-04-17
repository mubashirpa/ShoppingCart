package com.evaluation.shoppingcart.presentation.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.evaluation.shoppingcart.R
import com.evaluation.shoppingcart.domain.model.shoppingItems.ShoppingItem
import com.evaluation.shoppingcart.presentation.cart.components.CartListItem
import com.evaluation.shoppingcart.presentation.components.ErrorScreen
import com.evaluation.shoppingcart.presentation.components.LoadingScreen
import com.evaluation.shoppingcart.presentation.theme.ShoppingCartTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    uiState: CartUiState,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.cart))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            AnimatedVisibility(
                visible = uiState.cartItems.isNotEmpty(),
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
            ) {
                Column {
                    HorizontalDivider()
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .navigationBarsPadding(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = stringResource(R.string.items, uiState.subTotal))
                            Text(text = stringResource(R.string.tax, uiState.taxTotal))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.order_total, uiState.total),
                                style = MaterialTheme.typography.headlineSmall,
                            )
                        }
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = stringResource(R.string.place_order))
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        when {
            uiState.loading -> {
                LoadingScreen(modifier = Modifier.padding(innerPadding))
            }

            uiState.cartItems.isEmpty() -> {
                ErrorScreen(
                    onRetryClick = onNavigateUp,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    buttonText = stringResource(R.string.shop_now),
                    errorMessage = stringResource(R.string.your_cart_is_empty),
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = innerPadding,
                ) {
                    items(
                        items = uiState.cartItems,
                        key = { it.itemID!! },
                    ) { item ->
                        CartListItem(
                            onClick = {},
                            item = item,
                            modifier = Modifier.animateItem(),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CartScreenPreview() {
    ShoppingCartTheme {
        CartScreen(
            uiState =
                CartUiState(
                    cartItems =
                        listOf(
                            ShoppingItem(
                                itemID = "1",
                                itemName = "Item 1",
                            ),
                        ),
                ),
            onNavigateUp = {},
        )
    }
}

package com.evaluation.shoppingcart.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.evaluation.shoppingcart.presentation.cart.CartScreen
import com.evaluation.shoppingcart.presentation.cart.CartViewModel
import com.evaluation.shoppingcart.presentation.home.HomeScreen
import com.evaluation.shoppingcart.presentation.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShoppingCartNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.Home,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Screen.Home> {
            val viewModel: HomeViewModel = koinViewModel()
            HomeScreen(
                uiState = viewModel.uiState,
                onEvent = viewModel::onEvent,
                onNavigateToCart = {
                    navController.navigate(Screen.Cart)
                },
            )
        }
        composable<Screen.Cart> {
            val viewModel: CartViewModel = koinViewModel()
            CartScreen(
                uiState = viewModel.uiState,
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}

package com.evaluation.shoppingcart.di

import androidx.room.Room
import com.evaluation.shoppingcart.data.local.database.ShoppingCartDatabase
import com.evaluation.shoppingcart.data.repository.ShoppingCartRepositoryImpl
import com.evaluation.shoppingcart.data.repository.ShoppingItemsRepositoryImpl
import com.evaluation.shoppingcart.domain.repository.ShoppingCartRepository
import com.evaluation.shoppingcart.domain.repository.ShoppingItemsRepository
import com.evaluation.shoppingcart.domain.usecase.AddToShoppingCartUseCase
import com.evaluation.shoppingcart.domain.usecase.GetShoppingCartItemsUseCase
import com.evaluation.shoppingcart.domain.usecase.GetShoppingItemsUseCase
import com.evaluation.shoppingcart.presentation.cart.CartViewModel
import com.evaluation.shoppingcart.presentation.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        single {
            HttpClient {
                expectSuccess = true
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                            useAlternativeNames = false
                        },
                    )
                }
            }
        }
        singleOf(::ShoppingItemsRepositoryImpl) { bind<ShoppingItemsRepository>() }
        singleOf(::ShoppingCartRepositoryImpl) { bind<ShoppingCartRepository>() }
        single { GetShoppingItemsUseCase(get()) }
        single { AddToShoppingCartUseCase(get()) }
        single { GetShoppingCartItemsUseCase(get()) }
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        single {
            Room
                .databaseBuilder(
                    androidContext(),
                    ShoppingCartDatabase::class.java,
                    "shopping_cart_db",
                ).build()
        }
    }

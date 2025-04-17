package com.evaluation.shoppingcart

import android.app.Application
import com.evaluation.shoppingcart.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ShoppingCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ShoppingCartApplication)
            modules(appModule)
        }
    }
}

package it.lorisdaniel.jetcosttest

import android.app.Application
import it.lorisdaniel.jetcosttest.dependency.databaseModule
import it.lorisdaniel.jetcosttest.dependency.networkModule
import it.lorisdaniel.jetcosttest.dependency.repositoryModule
import it.lorisdaniel.jetcosttest.dependency.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JetCostTest : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JetCostTest)
            modules(listOf(networkModule, repositoryModule, databaseModule, viewModelModule))
        }
    }
}
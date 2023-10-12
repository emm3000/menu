package com.emm.betsy.presentation

import android.app.Application
import com.emm.betsy.di.AppContainer
import com.emm.betsy.di.DefaultAppContainer
import com.emm.betsy.di.commonModule
import com.emm.betsy.di.dataSourceModule
import com.emm.betsy.di.repositoryModule
import com.emm.betsy.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BetsyApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        startKoin {
            androidLogger()
            androidContext(this@BetsyApp)
            modules(
                repositoryModule,
                viewModelModule,
                commonModule,
                dataSourceModule
            )
        }
    }

}
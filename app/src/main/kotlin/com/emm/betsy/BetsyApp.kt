package com.emm.betsy

import android.app.Application
import com.emm.betsy.fastdi.AppContainer
import com.emm.betsy.fastdi.DefaultAppContainer
import com.emm.betsy.fastdi.commonModule
import com.emm.betsy.fastdi.dataSourceModule
import com.emm.betsy.fastdi.repositoryModule
import com.emm.betsy.fastdi.viewModelModule
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
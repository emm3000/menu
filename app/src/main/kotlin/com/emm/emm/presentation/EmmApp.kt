package com.emm.emm.presentation

import android.app.Application
import com.emm.emm.di.AppContainer
import com.emm.emm.di.DefaultAppContainer
import com.emm.emm.di.commonModule
import com.emm.emm.di.dataSourceModule
import com.emm.emm.di.repositoryModule
import com.emm.emm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EmmApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        startKoin {
            androidLogger()
            androidContext(this@EmmApp)
            modules(
                repositoryModule,
                viewModelModule,
                commonModule,
                dataSourceModule
            )
        }
    }

}
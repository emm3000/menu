package com.emm.betsy

import android.app.Application
import com.emm.betsy.fastdi.AppContainer
import com.emm.betsy.fastdi.DefaultAppContainer

class BetsyApp: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }

}
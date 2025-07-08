package com.emm.emm.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.emm.emm.EmmDatabase
import com.emm.emm.data.datasource.ItemLocalDataSource
import com.emm.emm.data.datasource.PersonDataSource
import com.emm.emm.data.datasource.PersonLocalDataSource

interface AppContainer {
    val sqlDriver: SqlDriver
    val personDataSource: PersonDataSource
    val itemLocalDataSource: ItemLocalDataSource
}

class DefaultAppContainer(private val appContext: Context) : AppContainer {

    override val sqlDriver: SqlDriver by lazy {
        AndroidSqliteDriver(
            schema = EmmDatabase.Schema,
            context = appContext,
            name = "betsy.db",
            callback = object : AndroidSqliteDriver.Callback(EmmDatabase.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }

    override val personDataSource: PersonDataSource by lazy {
        PersonLocalDataSource(
            db = EmmDatabase.invoke(sqlDriver)
        )
    }

    override val itemLocalDataSource: ItemLocalDataSource by lazy {
        ItemLocalDataSource(db = EmmDatabase.invoke(sqlDriver))
    }

}
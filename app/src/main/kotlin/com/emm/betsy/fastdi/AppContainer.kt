package com.emm.betsy.fastdi

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.emm.betsy.EmmDatabase
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.datasource.person.PersonDataSource
import com.emm.betsy.data.datasource.person.PersonDataSourceImpl

interface AppContainer {
    val sqlDriver: SqlDriver
    val personDataSource: PersonDataSource
    val itemDataSource: ItemDataSource
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
        PersonDataSourceImpl(
            db = EmmDatabase.invoke(sqlDriver)
        )
    }

    override val itemDataSource: ItemDataSource by lazy {
        ItemDataSource(db = EmmDatabase.invoke(sqlDriver))
    }

}
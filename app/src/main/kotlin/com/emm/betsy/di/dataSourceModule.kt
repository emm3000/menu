package com.emm.betsy.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.emm.betsy.EmmDatabase
import com.emm.betsy.data.datasource.ItemDataSource
import com.emm.betsy.data.datasource.ItemLocalDataSource
import com.emm.betsy.data.datasource.MenuDataSource
import com.emm.betsy.data.datasource.MenuItemDataSource
import com.emm.betsy.data.datasource.MenuItemLocalDataSource
import com.emm.betsy.data.datasource.MenuLocalDataSource
import com.emm.betsy.data.datasource.PersonDataSource
import com.emm.betsy.data.datasource.PersonLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    single { provideSqlDriver(androidContext()) }
    single { provideDb(get()) }
    single<PersonDataSource> { PersonLocalDataSource(get()) }
    single<ItemDataSource> { ItemLocalDataSource(get()) }
    single<MenuDataSource> { MenuLocalDataSource(get()) }
    single<MenuItemDataSource> { MenuItemLocalDataSource(get()) }
}

private fun provideSqlDriver(context: Context): SqlDriver {
    return AndroidSqliteDriver(
        schema = EmmDatabase.Schema,
        context = context,
        name = "rosa.db",
        callback = object : AndroidSqliteDriver.Callback(EmmDatabase.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.setForeignKeyConstraintsEnabled(true)
            }
        }
    )
}

private fun provideDb(sqlDriver: SqlDriver): EmmDatabase = EmmDatabase.invoke(sqlDriver)

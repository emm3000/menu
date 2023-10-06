package com.emm.betsy.fastdi

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.emm.betsy.EmmDatabase
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.datasource.menu.MenuDataSource
import com.emm.betsy.data.datasource.person.PersonDataSource
import com.emm.betsy.data.datasource.person.PersonDataSourceImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    single { provideSqlDriver(androidContext()) }
    single { provideDb(get()) }
    single<PersonDataSource> { PersonDataSourceImpl(get()) }
    single { ItemDataSource(get()) }
    single { MenuDataSource(get()) }
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

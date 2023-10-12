package com.emm.betsy.di

import com.emm.betsy.data.repository.ItemRepository
import com.emm.betsy.data.repository.MenuRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ItemRepository)
    singleOf(::MenuRepository)
}
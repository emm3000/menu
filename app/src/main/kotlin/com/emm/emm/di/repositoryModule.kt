package com.emm.emm.di

import com.emm.emm.data.repository.ItemRepository
import com.emm.emm.data.repository.MenuRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::ItemRepository)
    singleOf(::MenuRepository)
}
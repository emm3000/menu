package com.emm.betsy.fastdi

import com.emm.betsy.screen.home.HomeViewModel
import com.emm.betsy.screen.menu.AddItemViewModel
import com.emm.betsy.screen.menu.AddMenuViewModel
import com.emm.betsy.screen.menu.ItemListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
//    viewModelOf(::HomeViewModel)
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ItemListViewModel(get()) }
    viewModelOf(::AddItemViewModel)
    viewModelOf(::AddMenuViewModel)
}
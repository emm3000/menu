package com.emm.betsy.fastdi

import com.emm.betsy.screen.home.HomeViewModel
import com.emm.betsy.screen.menu.AddItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
//    viewModelOf(::HomeViewModel)
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AddItemViewModel(get()) }
}
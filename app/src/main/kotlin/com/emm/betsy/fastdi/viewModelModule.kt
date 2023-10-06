package com.emm.betsy.fastdi

import com.emm.betsy.screen.home.HomeViewModel
import com.emm.betsy.screen.additem.EditorItemViewModel
import com.emm.betsy.screen.menu.AddMenuViewModel
import com.emm.betsy.screen.menu.ItemListViewModel
import com.emm.betsy.screen.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.parameter.ParametersHolder
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { ItemListViewModel(get()) }
    viewModel { parameters: ParametersHolder -> EditorItemViewModel(
        parameters[0],
        parameters[1],
        parameters.get(),
        get()
    ) }
    viewModelOf(::AddMenuViewModel)
    viewModelOf(::MenuViewModel)
}
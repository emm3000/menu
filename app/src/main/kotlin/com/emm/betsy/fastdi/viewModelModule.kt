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
    viewModel { parameters: ParametersHolder ->
        EditorItemViewModel(
            itemId = parameters.get(),
            itemDataSource = get()
        )
    }
    viewModelOf(::AddMenuViewModel)
    viewModelOf(::MenuViewModel)
}
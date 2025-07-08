package com.emm.emm.di

import com.emm.emm.presentation.screen.home.HomeViewModel
import com.emm.emm.presentation.screen.item.EditorItemViewModel
import com.emm.emm.presentation.screen.menu.AddMenuViewModel
import com.emm.emm.presentation.screen.menu.ItemListViewModel
import com.emm.emm.presentation.screen.menu.MenuViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.parameter.ParametersHolder
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { ItemListViewModel(get()) }
    viewModel { parameters: ParametersHolder ->
        EditorItemViewModel(
            itemId = parameters.get(),
            itemRepository = get()
        )
    }
    viewModelOf(::AddMenuViewModel)
    viewModelOf(::MenuViewModel)
}
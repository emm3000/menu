package com.emm.betsy.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emm.betsy.data.datasource.ItemDataSource
import com.emm.betsy.data.datasource.ItemLocalDataSource
import com.emm.betsy.data.datasource.PersonDataSource
import com.github.javafaker.Faker

class HomeViewModel(
    private val personDataSource: PersonDataSource,
    private val menuDataSource: ItemDataSource,
) : ViewModel() {


}

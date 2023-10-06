package com.emm.betsy.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emm.betsy.data.datasource.item.ItemDataSource
import com.emm.betsy.data.datasource.person.PersonDataSource
import com.github.javafaker.Faker

class HomeViewModel(
    private val personDataSource: PersonDataSource,
    private val menuDataSource: ItemDataSource,
    private val faker: Faker
) : ViewModel() {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideInstance(personDataSource: PersonDataSource, itemDataSource: ItemDataSource): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(personDataSource, itemDataSource, Faker.instance()) as T
                }
            }
        }
    }

}

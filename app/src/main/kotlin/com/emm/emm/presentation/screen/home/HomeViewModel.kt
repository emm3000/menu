package com.emm.emm.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.emm.data.repository.MenuRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            menuRepository.createMenuByDate()
        }
    }


}

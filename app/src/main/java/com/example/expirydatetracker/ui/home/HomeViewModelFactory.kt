package com.example.expirydatetracker.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expirydatetracker.domain.useCase.GetItemsUseCase
import com.example.expirydatetracker.domain.useCase.SaveItemsUseCase

class HomeViewModelFactory(
    private val app: Application,
    private val getItemsUseCase: GetItemsUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(app, getItemsUseCase) as T
    }
}


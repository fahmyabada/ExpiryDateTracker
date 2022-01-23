package com.example.expirydatetracker.ui.updateItem

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expirydatetracker.domain.useCase.GetItemsByIdUseCase
import com.example.expirydatetracker.domain.useCase.UpdateItemsUseCase

class UpdateItemViewModelFactory(
    private val app: Application,
    private val getItemsByIdUseCase: GetItemsByIdUseCase,
    private val updateItemsUseCase: UpdateItemsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UpdateItemViewModel(app, getItemsByIdUseCase, updateItemsUseCase) as T
    }
}


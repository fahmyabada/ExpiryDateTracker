package com.example.expirydatetracker.ui.scanCode

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expirydatetracker.domain.useCase.SaveItemsUseCase

class ScanCodeViewModelFactory(
    private val app: Application,
    private val saveItemsUseCase: SaveItemsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScanCodeViewModel(app,  saveItemsUseCase) as T
    }
}


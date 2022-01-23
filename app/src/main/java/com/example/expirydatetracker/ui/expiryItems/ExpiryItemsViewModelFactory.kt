package com.example.expirydatetracker.ui.expiryItems

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expirydatetracker.domain.useCase.GetItemsExpiryUseCase

class ExpiryItemsViewModelFactory(
    private val app: Application,
    private val getItemsExpiryUseCase: GetItemsExpiryUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpiryItemsViewModel(app, getItemsExpiryUseCase) as T
    }
}


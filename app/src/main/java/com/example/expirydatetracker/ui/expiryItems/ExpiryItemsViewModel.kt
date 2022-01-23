package com.example.expirydatetracker.ui.expiryItems

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.example.expirydatetracker.domain.useCase.GetItemsExpiryUseCase
import kotlinx.coroutines.flow.collect

class ExpiryItemsViewModel(
    app: Application,
    private val getItemsExpiryUseCase: GetItemsExpiryUseCase
) : AndroidViewModel(app) {

    fun getItemsExpiry() = liveData {
        getItemsExpiryUseCase.execute().collect {
           emit(it)
       }
    }
}






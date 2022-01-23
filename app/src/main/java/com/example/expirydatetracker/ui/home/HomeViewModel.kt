package com.example.expirydatetracker.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.useCase.GetItemsUseCase
import com.example.expirydatetracker.domain.useCase.SaveItemsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    app: Application,
    private val getItemsUseCase: GetItemsUseCase,
) : AndroidViewModel(app) {

    fun getItems() = liveData {
       getItemsUseCase.execute().collect {
           emit(it)
       }
    }
}






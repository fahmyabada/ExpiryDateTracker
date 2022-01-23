package com.example.expirydatetracker.ui.scanCode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.useCase.SaveItemsUseCase
import kotlinx.coroutines.launch

class ScanCodeViewModel(
    app: Application,
    private val saveItemsUseCase: SaveItemsUseCase
) : AndroidViewModel(app) {

    val data: MutableLiveData<Unit> = MutableLiveData()

    fun saveItems(item: Item) = viewModelScope.launch {
         data.postValue(saveItemsUseCase.execute(item))
    }
}






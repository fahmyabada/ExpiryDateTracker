package com.example.expirydatetracker.ui.updateItem

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.expirydatetracker.domain.useCase.GetItemsByIdUseCase
import com.example.expirydatetracker.domain.useCase.UpdateItemsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UpdateItemViewModel(
    app: Application,
    private val getItemsByIdUseCase: GetItemsByIdUseCase,
    private val updateItemsUseCase: UpdateItemsUseCase
) : AndroidViewModel(app) {
    val update: MutableLiveData<Unit> = MutableLiveData()

    fun getItemsById(id: Int) = liveData {
        getItemsByIdUseCase.execute(id).collect {
            emit(it)
        }
    }

    fun updateItem(id: Int, name: String, category: String, date: String) = viewModelScope.launch  {
        update.postValue(updateItemsUseCase.execute(id, name, category, date))
    }
}






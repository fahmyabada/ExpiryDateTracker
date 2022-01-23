package com.example.expirydatetracker.ui.updateItem

import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.data.repository.FakeItemsRepository
import com.example.expirydatetracker.domain.useCase.GetItemsByIdUseCase
import com.example.expirydatetracker.domain.useCase.UpdateItemsUseCase
import com.example.expirydatetracker.ui.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpdateItemViewModelTest {

    private lateinit var viewModel: UpdateItemViewModel
    private lateinit var getItemsByIdUseCase: GetItemsByIdUseCase
    private lateinit var updateItemsUseCase: UpdateItemsUseCase
    private lateinit var fakeItemsRepository: FakeItemsRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeItemsRepository = FakeItemsRepository()
        getItemsByIdUseCase = GetItemsByIdUseCase(fakeItemsRepository)
        updateItemsUseCase = UpdateItemsUseCase(fakeItemsRepository)
        val context = ApplicationProvider.getApplicationContext<Application>()
        viewModel = UpdateItemViewModel(context, getItemsByIdUseCase, updateItemsUseCase)
    }


    @Test
    fun testGetItemsById() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-23 04:18"
        )

        fakeItemsRepository.saveItem(item)
        val data = viewModel.getItemsById(1).getOrAwaitValue()

        Truth.assertThat(data).isEqualTo(item)
    }

    @Test
    fun testUpdateItem() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-23 04:18"
        )

        fakeItemsRepository.saveItem(item)

        viewModel.updateItem(id = 1, name = "ketofan2", category = "Medicine2", date = "2022-01-22 04:18")
        val data = fakeItemsRepository.getItem().take(1).toList()[0][0]
        println("Hello2 StackOverflow $item")
        Truth.assertThat(data).isEqualTo(item)
    }
}



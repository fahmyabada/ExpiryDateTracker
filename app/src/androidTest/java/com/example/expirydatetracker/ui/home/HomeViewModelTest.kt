package com.example.expirydatetracker.ui.home

import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.data.repository.FakeItemsRepository
import com.example.expirydatetracker.domain.useCase.GetItemsUseCase
import com.example.expirydatetracker.ui.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest{
    private lateinit var viewModel: HomeViewModel
    private lateinit var getItemsUseCase: GetItemsUseCase
    private lateinit var fakeItemsRepository: FakeItemsRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeItemsRepository = FakeItemsRepository()
        getItemsUseCase = GetItemsUseCase(fakeItemsRepository)
        val context = ApplicationProvider.getApplicationContext<Application>()
        viewModel = HomeViewModel(context, getItemsUseCase)
    }


    @Test
    fun testGetItems() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-23 04:18"
        )
        fakeItemsRepository.saveItem(item)
        val data = viewModel.getItems().getOrAwaitValue()
        data.forEach {
            Truth.assertThat(it).isEqualTo(item)
        }
    }
}
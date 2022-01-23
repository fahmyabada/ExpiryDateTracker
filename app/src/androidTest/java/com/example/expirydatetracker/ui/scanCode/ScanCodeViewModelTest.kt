package com.example.expirydatetracker.ui.scanCode

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.data.repository.FakeItemsRepository
import com.example.expirydatetracker.domain.useCase.SaveItemsUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScanCodeViewModelTest {

    private lateinit var viewModel: ScanCodeViewModel
    private lateinit var saveItemsUseCase: SaveItemsUseCase
    private lateinit var fakeItemsRepository: FakeItemsRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeItemsRepository = FakeItemsRepository()
        saveItemsUseCase = SaveItemsUseCase(fakeItemsRepository)
        val context = ApplicationProvider.getApplicationContext<Application>()
        viewModel = ScanCodeViewModel(context, saveItemsUseCase)
    }


    @Test
    fun testSaveItems() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-23 04:18"
        )
        viewModel.saveItems(item)
        val data = fakeItemsRepository.getItem().take(1).toList()[0][0]
        Truth.assertThat(data).isEqualTo(item)
    }
}
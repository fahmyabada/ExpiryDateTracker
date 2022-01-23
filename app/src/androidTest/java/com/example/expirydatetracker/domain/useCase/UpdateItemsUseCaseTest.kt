package com.example.expirydatetracker.domain.useCase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.data.repository.FakeItemsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UpdateItemsUseCaseTest{
    private lateinit var fakeItemsRepository: FakeItemsRepository
    private lateinit var updateItemsUseCase: UpdateItemsUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeItemsRepository = FakeItemsRepository()
        updateItemsUseCase = UpdateItemsUseCase(fakeItemsRepository)
    }

    @Test
    fun testExecute() = runBlocking {

        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-23 04:18"
        )
        fakeItemsRepository.saveItem(item)

        updateItemsUseCase.execute(1,"ketofan2", "Medicine2","2022-01-23 04:18")

        val data = fakeItemsRepository.getItem().take(1).toList()[0][0]

        Truth.assertThat(data).isEqualTo(item)
    }
}
package com.example.expirydatetracker.domain.useCase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.data.repository.FakeItemsRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetCheckItemsExpiryUseCaseTest{
    private lateinit var fakeItemsRepository: FakeItemsRepository
    private lateinit var getCheckItemsExpiryUseCase: GetCheckItemsExpiryUseCase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeItemsRepository = FakeItemsRepository()
        getCheckItemsExpiryUseCase = GetCheckItemsExpiryUseCase(fakeItemsRepository)
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

        val data = getCheckItemsExpiryUseCase.execute()

        data.forEach {
            Truth.assertThat(it).isEqualTo(item)
        }
    }
}
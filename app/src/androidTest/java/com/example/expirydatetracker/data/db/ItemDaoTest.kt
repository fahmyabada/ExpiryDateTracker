package com.example.expirydatetracker.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.expirydatetracker.data.dao.ExpireItemsDatabase
import com.example.expirydatetracker.data.dao.ItemDao
import com.example.expirydatetracker.data.model.Item
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    private lateinit var itemDao: ItemDao
    private lateinit var expireItemsDatabase: ExpireItemsDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        expireItemsDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ExpireItemsDatabase::class.java,
        ).build()

        itemDao = expireItemsDatabase.itemDao()
    }

    @After
    fun tearDown() {
        expireItemsDatabase.close()
    }

    @Test
    fun saveItem() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-23 04:18"
        )
        itemDao.saveItem(item)

        itemDao.getAllItems().forEach { it2 ->
            assertThat(it2).isEqualTo(item)
        }
    }

    @Test
    fun getItemsExpiry() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-21 04:18"
        )
        itemDao.saveItem(item)
        val s = itemDao.getItemsExpiry().take(1).toList()[0][0]
        assertThat(s).isEqualTo(item)
    }

    @Test
    fun getItemsById() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-21 04:18"
        )
        itemDao.saveItem(item)
        val s = itemDao.getItemsById(1)
        assertThat(s).isEqualTo(item)
    }

    @Test
    fun getCheckItemsExpiry() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-21 04:18"
        )
        itemDao.saveItem(item)
        itemDao.getCheckItemsExpiry().forEach { it2 ->
            assertThat(it2).isEqualTo(item)
        }
    }

    @Test
    fun updateStatusExpiry() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-21 04:18"
        )
        itemDao.saveItem(item)

        CoroutineScope(Dispatchers.IO).launch {
            itemDao.updateStatusExpiry(1)
        }
        itemDao.getAllItems().forEach { it2 ->
            assertThat(it2).isEqualTo(item)
        }
    }

    @Test
    fun updateItem() = runBlocking {
        val item = Item(
            id = 1,
            name = "ketofan",
            category = "Medicine",
            statusExpiry = 1,
            date = "2022-01-21 04:18"
        )
        itemDao.saveItem(item)

        CoroutineScope(Dispatchers.IO).launch {
            itemDao.updateItem(1,"ketofan2","Medicine2","2022-01-23 04:18")
        }

        itemDao.getAllItems().forEach { it2 ->
            assertThat(it2).isEqualTo(item)
        }
    }
}
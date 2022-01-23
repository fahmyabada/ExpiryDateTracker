package com.example.expirydatetracker.data.repository

import com.example.expirydatetracker.data.dao.ItemDao
import com.example.expirydatetracker.data.model.Item
import kotlinx.coroutines.flow.Flow

class ItemsDataSourceImpl(private val itemDao: ItemDao) :
    ItemsDataSource {
    override fun getItemsExpiry(): Flow<List<Item>> {
        return itemDao.getItemsExpiry()
    }

    override fun getItem(): Flow<List<Item>> {
        return itemDao.getItems()
    }

    override fun getItemsById(id: Int): Flow<Item> {
        return itemDao.getItemsById(id)
    }

    override fun getCheckItemsExpiry(): List<Item> {
        return itemDao.getCheckItemsExpiry()
    }

    override suspend fun updateItem(id: Int, name: String, category: String, date: String) {
        itemDao.updateItem(id, name, category, date)
    }

    override fun updateStatusExpiry(id: Int) {
        itemDao.updateStatusExpiry(id)
    }

    override suspend fun saveItem(item: Item) {
        itemDao.saveItem(item)
    }
}
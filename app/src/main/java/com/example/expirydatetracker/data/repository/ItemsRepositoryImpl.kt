package com.example.expirydatetracker.data.repository

import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class ItemsRepositoryImpl(private val itemsDataSource: ItemsDataSource) : ItemsRepository {
    override fun getItemsExpiry(): Flow<List<Item>> {
        return itemsDataSource.getItemsExpiry()
    }

    override fun getItem(): Flow<List<Item>> {
        return itemsDataSource.getItem()
    }

    override fun getItemsById(id: Int): Flow<Item> {
        return itemsDataSource.getItemsById(id)
    }

    override fun getCheckItemsExpiry(): List<Item> {
        return itemsDataSource.getCheckItemsExpiry()
    }

    override fun updateStatusExpiry(id: Int) {
        return itemsDataSource.updateStatusExpiry(id)
    }

    override suspend fun updateItem(id: Int, name: String, category: String, date: String) {
        itemsDataSource.updateItem(id, name, category, date)
    }

    override suspend fun saveItem(item: Item) {
        itemsDataSource.saveItem(item)
    }

}
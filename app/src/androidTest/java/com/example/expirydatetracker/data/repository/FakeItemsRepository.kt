package com.example.expirydatetracker.data.repository

import com.example.expirydatetracker.data.model.Item
import com.example.expirydatetracker.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeItemsRepository : ItemsRepository {

    private val item = mutableListOf<Item>()

    override fun getItemsExpiry(): Flow<List<Item>> {
        return flow { emit(item) }
    }

    override fun getItem(): Flow<List<Item>> {
        return flow { emit(item) }
    }

    override fun getItemsById(id: Int): Flow<Item> {
        return flow { emit(item.find { it.id == id }!!) }
    }

    override fun getCheckItemsExpiry(): List<Item> {
        return item
    }

    override fun updateStatusExpiry(id: Int) {
        val data = item.find { it.id == id }!!
        data.statusExpiry = 0
    }

    override suspend fun updateItem(id: Int, name: String, category: String, date: String) {
        val data = item.find { it.id == id }!!
        data.name = name
        data.category = category
        data.date = date
    }

    override suspend fun saveItem(item: Item) {
        this.item.add(item)
    }
}
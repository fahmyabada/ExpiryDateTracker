package com.example.expirydatetracker.data.repository

import com.example.expirydatetracker.data.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemsDataSource {
    fun getItemsExpiry(): Flow<List<Item>>
    fun getItem(): Flow<List<Item>>
    fun getItemsById(id: Int): Flow<Item>
    fun getCheckItemsExpiry(): List<Item>
    fun updateStatusExpiry(id: Int)
    suspend fun updateItem(id: Int, name: String, category: String, date: String)
    suspend fun saveItem(item: Item)
}
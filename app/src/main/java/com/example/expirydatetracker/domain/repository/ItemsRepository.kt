package com.example.expirydatetracker.domain.repository

import com.example.expirydatetracker.data.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getItemsExpiry(): Flow<List<Item>>
    fun getItem(): Flow<List<Item>>
    fun getItemsById(id: Int): Flow<Item>
    fun getCheckItemsExpiry(): List<Item>
    fun updateStatusExpiry(id: Int)
    suspend fun updateItem(id: Int, name: String, category: String, date: String)
    suspend fun saveItem(item: Item)
}
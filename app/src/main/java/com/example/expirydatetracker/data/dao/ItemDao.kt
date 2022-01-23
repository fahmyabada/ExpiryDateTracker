package com.example.expirydatetracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expirydatetracker.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert
    suspend fun saveItem(item: Item)

    @Query("select * from Item where date >= datetime('now') ORDER by date DESC")
    fun getItems(): Flow<List<Item>>

    @Query("select * from Item ")
    fun getAllItems(): List<Item>

    @Query("select * from Item where date <= datetime('now') ORDER by date DESC")
    fun getItemsExpiry(): Flow<List<Item>>

    @Query("select * from Item where id =:id ")
    fun getItemsById(id: Int): Flow<Item>

    @Query("select * from Item where date <= datetime('now') and statusExpiry = 1")
    fun getCheckItemsExpiry(): List<Item>

    @Query("update Item set statusExpiry = 0 where id =:id")
    fun updateStatusExpiry(id: Int)

    @Query("update Item set name =:name, category =:category,date =:date where id =:id")
    suspend fun updateItem(id: Int, name: String, category: String, date: String)
}
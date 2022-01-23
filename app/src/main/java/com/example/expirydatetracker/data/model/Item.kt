package com.example.expirydatetracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "statusExpiry")
    var statusExpiry: Int
)
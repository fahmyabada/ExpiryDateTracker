package com.example.expirydatetracker.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expirydatetracker.data.model.Item

@Database(entities = [Item::class], version = 1 , exportSchema = false)
abstract class ExpireItemsDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var db_instance: ExpireItemsDatabase? = null

        fun getAPPDatabaseInstance(context: Context): ExpireItemsDatabase {
            if (db_instance == null) {
                db_instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpireItemsDatabase::class.java,
                    "Items.db"
                ).build()
            }

            return db_instance!!
        }
    }
}
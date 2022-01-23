package com.example.expirydatetracker.ui.di.core

import android.content.Context
import com.example.expirydatetracker.data.dao.ExpireItemsDatabase
import com.example.expirydatetracker.data.dao.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Singleton
    @Provides
    fun provideExpireDateDataBase(@ApplicationContext context: Context): ExpireItemsDatabase {
        return ExpireItemsDatabase.getAPPDatabaseInstance(context.applicationContext)
    }

    @Singleton
    @Provides
    fun provideExpireDateDao(expireItemsDatabase: ExpireItemsDatabase): ItemDao {
        return expireItemsDatabase.itemDao()
    }

}
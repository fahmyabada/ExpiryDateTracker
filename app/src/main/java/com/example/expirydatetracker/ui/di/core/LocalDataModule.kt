package com.example.expirydatetracker.ui.di.core

import com.example.expirydatetracker.data.dao.ItemDao
import com.example.expirydatetracker.data.repository.ItemsDataSource
import com.example.expirydatetracker.data.repository.ItemsDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideItemsDataSource(itemDao: ItemDao):ItemsDataSource{
        return ItemsDataSourceImpl(itemDao)
    }

}
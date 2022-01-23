package com.example.expirydatetracker.ui.di.core

import com.example.expirydatetracker.data.repository.ItemsDataSource
import com.example.expirydatetracker.data.repository.ItemsRepositoryImpl
import com.example.expirydatetracker.domain.repository.ItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideitemsRepository(
        itemsDataSource: ItemsDataSource
    ): ItemsRepository {

        return ItemsRepositoryImpl(
            itemsDataSource,
        )
    }

}
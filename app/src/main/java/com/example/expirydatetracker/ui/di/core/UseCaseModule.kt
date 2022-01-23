package com.example.expirydatetracker.ui.di.core

import com.example.expirydatetracker.domain.repository.ItemsRepository
import com.example.expirydatetracker.domain.useCase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetItemsUseCase(itemsRepository: ItemsRepository): GetItemsUseCase {
        return GetItemsUseCase(itemsRepository)
    }

    @Provides
    fun provideSaveItemsUseCase(itemsRepository: ItemsRepository): SaveItemsUseCase {
        return SaveItemsUseCase(itemsRepository)
    }

    @Provides
    fun provideGetItemsExpiryUseCase(itemsRepository: ItemsRepository): GetItemsExpiryUseCase {
        return GetItemsExpiryUseCase(itemsRepository)
    }

    @Provides
    fun provideGetItemsByIdUseCase(itemsRepository: ItemsRepository): GetItemsByIdUseCase {
        return GetItemsByIdUseCase(itemsRepository)
    }

    @Provides
    fun provideUpdateItemsUseCase(itemsRepository: ItemsRepository): UpdateItemsUseCase {
        return UpdateItemsUseCase(itemsRepository)
    }

    @Provides
    fun provideGetCheckItemsExpiryUseCase(itemsRepository: ItemsRepository): GetCheckItemsExpiryUseCase {
        return GetCheckItemsExpiryUseCase(itemsRepository)
    }

    @Provides
    fun provideUpdateStatusExpiryUseCase(itemsRepository: ItemsRepository): UpdateStatusExpiryUseCase {
        return UpdateStatusExpiryUseCase(itemsRepository)
    }
}
package com.example.expirydatetracker.ui.di.core

import android.app.Application
import com.example.expirydatetracker.domain.useCase.*
import com.example.expirydatetracker.ui.expiryItems.ExpiryItemsViewModelFactory
import com.example.expirydatetracker.ui.home.HomeViewModelFactory
import com.example.expirydatetracker.ui.scanCode.ScanCodeViewModelFactory
import com.example.expirydatetracker.ui.updateItem.UpdateItemViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(
        application: Application,
        getItemsUseCase: GetItemsUseCase,
    ): HomeViewModelFactory {
        return HomeViewModelFactory(application, getItemsUseCase)
    }

    @Singleton
    @Provides
    fun provideScanCodeViewModelFactory(
        application: Application,
        saveItemsUseCase: SaveItemsUseCase
    ): ScanCodeViewModelFactory {
        return ScanCodeViewModelFactory(application, saveItemsUseCase)
    }

    @Singleton
    @Provides
    fun provideExpiryDateViewModelFactory(
        application: Application,
        getItemsExpiryUseCase: GetItemsExpiryUseCase
    ): ExpiryItemsViewModelFactory {
        return ExpiryItemsViewModelFactory(application, getItemsExpiryUseCase)
    }

    @Singleton
    @Provides
    fun provideUpdateItemViewModelFactory(
        application: Application,
        getItemsByIdUseCase: GetItemsByIdUseCase,
        updateItemsUseCase: UpdateItemsUseCase
    ): UpdateItemViewModelFactory {
        return UpdateItemViewModelFactory(application, getItemsByIdUseCase, updateItemsUseCase)
    }
}
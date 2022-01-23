package com.example.expirydatetracker.ui.di.expiryItems

import com.example.expirydatetracker.ui.expiryItems.CustomHolderExpiryDate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModuleExpiryItems {

    @Singleton
    @Provides
    fun provideCustomHolderExpiryDate():CustomHolderExpiryDate{
        return CustomHolderExpiryDate()
    }
}
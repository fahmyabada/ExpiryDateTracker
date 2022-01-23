package com.example.expirydatetracker.ui.di.home

import com.example.expirydatetracker.ui.home.CustomHolderHome
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModuleHome {

    @Singleton
    @Provides
    fun provideCustomHolderHome():CustomHolderHome{
        return CustomHolderHome()
    }
}
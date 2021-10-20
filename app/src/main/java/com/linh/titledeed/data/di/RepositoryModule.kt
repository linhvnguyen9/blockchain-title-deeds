package com.linh.titledeed.data.di

import com.linh.titledeed.data.repository.TitleDeedRepositoryImpl
import com.linh.titledeed.data.repository.WalletRepositoryImpl
import com.linh.titledeed.domain.repository.TitleDeedRepository
import com.linh.titledeed.domain.repository.WalletRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    abstract fun bindWalletRepository(walletRepositoryImpl: WalletRepositoryImpl): WalletRepository

    @Binds
    abstract fun bindTitleDeedRepository(titleDeedRepositoryImpl: TitleDeedRepositoryImpl): TitleDeedRepository
}
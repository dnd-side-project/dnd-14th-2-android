package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.FcmTokenRepositoryImpl
import com.smtm.pickle.domain.repository.FcmTokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFcmTokenRepository(impl: FcmTokenRepositoryImpl): FcmTokenRepository
}
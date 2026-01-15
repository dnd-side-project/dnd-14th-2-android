package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.AuthRepositoryImpl
import com.smtm.pickle.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}

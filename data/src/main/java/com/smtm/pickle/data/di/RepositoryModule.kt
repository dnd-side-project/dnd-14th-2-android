package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.FakeNicknameRepository
import com.smtm.pickle.domain.repository.NicknameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNicknameRepository(impl: FakeNicknameRepository): NicknameRepository
}

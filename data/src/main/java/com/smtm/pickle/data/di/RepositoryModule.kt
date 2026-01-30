package com.smtm.pickle.data.di

import com.smtm.pickle.data.repository.FakeNicknameRepository
import com.smtm.pickle.data.repository.ledger.LedgerRepositoryImpl
import com.smtm.pickle.domain.repository.NicknameRepository
import com.smtm.pickle.domain.repository.ledger.LedgerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNicknameRepository(impl: FakeNicknameRepository): NicknameRepository

    @Binds
    @Singleton
    abstract fun bindLedgerRepository(impl: LedgerRepositoryImpl): LedgerRepository
}
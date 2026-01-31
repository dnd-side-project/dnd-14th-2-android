package com.smtm.pickle.data.di

import com.smtm.pickle.data.source.remote.api.FakeLedgerApi
import com.smtm.pickle.data.source.remote.api.LedgerApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


// TODO Retrofit API 연동 시 제거
@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun bindLedgerApi(
        ledgerApi: FakeLedgerApi
    ): LedgerApi
}
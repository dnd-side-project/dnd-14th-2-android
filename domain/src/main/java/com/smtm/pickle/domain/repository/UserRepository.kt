package com.smtm.pickle.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun setOnboardingCompleted(completed: Boolean)

    fun isOnboardingCompleted(): Flow<Boolean>
}
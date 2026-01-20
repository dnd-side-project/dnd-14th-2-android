package com.smtm.pickle.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.smtm.pickle.domain.model.auth.AuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "token")

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore
    private val dataStoreFlow = dataStore.data.catch { emit(emptyPreferences()) }

    suspend fun saveToken(token: AuthToken) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token.access
            preferences[REFRESH_TOKEN_KEY] = token.refresh
        }
    }

    suspend fun getToken(): AuthToken? = dataStoreFlow.first().getAuthToken()

    suspend fun getRefreshToken(): String? = dataStoreFlow.first()[REFRESH_TOKEN_KEY]

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    /** UI 관찰용 엑세스 토큰 획득 */
    fun getAccessTokenFlow(): Flow<String?> = dataStoreFlow.map { preferences ->
        preferences[ACCESS_TOKEN_KEY]
    }

    fun getTokenFlow(): Flow<AuthToken?> = dataStoreFlow.map { preferences ->
        preferences.getAuthToken()
    }

    fun isLoggedInFlow(): Flow<Boolean> = getAccessTokenFlow().map { it != null }

    private fun Preferences.getAuthToken(): AuthToken? {
        val accessToken = this[ACCESS_TOKEN_KEY]
        val refreshToken = this[REFRESH_TOKEN_KEY]

        return if (accessToken != null && refreshToken != null) {
            AuthToken(accessToken, refreshToken)
        } else {
            null
        }
    }

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}

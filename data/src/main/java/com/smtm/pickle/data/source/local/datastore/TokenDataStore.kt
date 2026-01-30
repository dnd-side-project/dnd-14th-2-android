package com.smtm.pickle.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.smtm.pickle.data.source.local.security.TokenEncryption
import com.smtm.pickle.domain.model.auth.AuthToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "token")

@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tokenEncryption: TokenEncryption
) {
    private val dataStore = context.dataStore
    private val dataStoreFlow = dataStore.data.catch { e ->
        if (e is IOException) emit(emptyPreferences()) else throw e
    }

    suspend fun saveToken(token: AuthToken) {
        val encryptedRefresh = tokenEncryption.encrypt(token.refresh)
        if (encryptedRefresh == null) {
            Timber.e("토큰 암호화 실패")
            return
        }

        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token.access
            preferences[REFRESH_TOKEN_KEY] = encryptedRefresh
        }
    }

    suspend fun getToken(): AuthToken? = dataStoreFlow.first().getAuthToken()

    suspend fun getRefreshToken(): String? {
        val encryptedToken = dataStoreFlow.first()[REFRESH_TOKEN_KEY]
        return encryptedToken?.let { tokenEncryption.decrypt(it) }
    }

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
        val encryptedRefresh = this[REFRESH_TOKEN_KEY]

        if (accessToken == null || encryptedRefresh == null) return null

        val refresh = tokenEncryption.decrypt(encryptedRefresh)

        return if (refresh != null) {
            AuthToken(accessToken, refresh)
        } else {
            Timber.e("토큰 복호화 실패")
            null
        }
    }

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }
}

package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.LoginRequest
import com.smtm.pickle.data.source.remote.model.LoginResponse
import com.smtm.pickle.data.source.remote.model.RefreshRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun socialLogin(@Body request: LoginRequest): LoginResponse

    @POST("auth/google")
    suspend fun signInWithGoogle(@Body idToken: String): LoginResponse

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshRequest: RefreshRequest): LoginResponse
}

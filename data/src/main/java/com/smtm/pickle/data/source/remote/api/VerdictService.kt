package com.smtm.pickle.data.source.remote.api

import com.smtm.pickle.data.source.remote.model.VerdictRequestsResponse
import com.smtm.pickle.data.source.remote.model.VerdictStatusResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VerdictService {

    @GET("verdicts/requests")
    suspend fun getVerdictRequests(
        @Query("status") status: String? = null,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VerdictRequestsResponse

    @GET("verdicts/requests/status")
    suspend fun getRequestedStatus(): VerdictStatusResponse
}
package com.example.drugsearchandtracker.data.remote.api

import com.example.drugsearchandtracker.data.remote.model.DrugResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RxNavApi {
    @GET("REST/drugs.json")
    suspend fun searchDrugs(
        @Query("name") drugName: String,
        @Query("expand") expand: String = "psn"
    ): DrugResponse
} 
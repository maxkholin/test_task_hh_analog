package com.example.testtaskeffectivemobile.data.api

import com.example.testtaskeffectivemobile.data.model.ServerResponse
import retrofit2.http.GET

interface ApiService {

    @GET("uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download")
    suspend fun loadData(): ServerResponse
}
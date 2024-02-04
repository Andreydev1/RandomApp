package com.example.randomapp.api.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomApi {
    @GET("/api/")
    fun getUsers(@Query("api-key") key: String, @Query("results") results: Int): Call<UserResponse>
}
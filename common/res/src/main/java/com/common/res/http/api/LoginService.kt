package com.common.res.http.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
     fun login(@Body map: Map<String, String>): Call<LoginEntity>
}
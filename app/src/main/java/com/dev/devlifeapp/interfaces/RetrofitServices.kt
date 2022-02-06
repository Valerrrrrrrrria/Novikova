package com.dev.devlifeapp.interfaces

import com.dev.devlifeapp.model.Life
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServices {
    @GET("{topic}/0?json=true")
    fun getPostsList(@Path("topic") topic: String): Call<Life>
}
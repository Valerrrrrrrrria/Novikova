package com.dev.devlifeapp.interfaces

import com.dev.devlifeapp.model.Life
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServices {
    @GET("{topic}/{id}?json=true")
    fun getPostsList(@Path("topic") topic: String, @Path("id") id: Int): Call<Life>
}
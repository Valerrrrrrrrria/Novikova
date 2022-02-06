package com.dev.devlifeapp.common

import com.dev.devlifeapp.interfaces.RetrofitServices
import com.dev.devlifeapp.retrofit.RetrofitClient

object Common {
    private val BASE_URL = "https://developerslife.ru"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
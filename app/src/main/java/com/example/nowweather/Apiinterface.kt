package com.example.nowweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Apiinterface {
    @GET("recipes/list")
    @Headers("X-RapidAPI-Key:215637e960msh811cba515dadabfp18e421jsn1904966e9dce",
        "X-RapidAPI-Host:tasty.p.rapidapi.com")

    fun getdata(@Query("from")from: Number,
                @Query("size")size: Number): Call<Apidata>
}
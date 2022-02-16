package com.hawkins.privatewether.logic.network

import com.hawkins.privatewether.SunnyWeatherApplication
import com.hawkins.privatewether.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query : String) : Call<PlaceResponse>
}
package com.hawkins.privatewether.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String ,val result : Result) {
    data class Result(val realTime: RealTime)
    data class RealTime(val temperature: Float, val skycon: String, @SerializedName("air_quality") val airQuality: AirQuality)
    data class AirQuality(val aqi: Aqi)
    data class Aqi(val chn: Float)
}
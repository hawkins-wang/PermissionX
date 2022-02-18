package com.hawkins.privatewether.logic

import androidx.lifecycle.liveData
import com.hawkins.privatewether.logic.dao.PlaceDao
import com.hawkins.privatewether.logic.model.Place
import com.hawkins.privatewether.logic.model.Weather
import com.hawkins.privatewether.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace(): Place = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun searchPlace(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        //开启协程，并利用async函数，保证两个网络请求成功相应后再进行下一步操作
        coroutineScope {
            val dailyWeather = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeWeather = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val dailyResponse = dailyWeather.await()
            val realtimeResponse = realtimeWeather.await()
            if (dailyResponse.status == "ok" && realtimeResponse.status == "ok") {
                val weather = Weather(
                    daily = dailyResponse.result.daily,
                    realTime = realtimeResponse.result.realTime
                )
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "dailyResponse status is ${dailyResponse.status} + " +
                                "realtimeResponse status is ${realtimeResponse.status}"
                    )
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
}
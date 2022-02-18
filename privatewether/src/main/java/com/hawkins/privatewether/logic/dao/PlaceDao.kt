package com.hawkins.privatewether.logic.dao

import android.content.Context
import com.google.gson.Gson
import com.hawkins.privatewether.SunnyWeatherApplication
import com.hawkins.privatewether.logic.model.Place

object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreference().edit().putString("place", Gson().toJson(place)).apply()
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreference().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreference().contains("place")

    private fun sharedPreference() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", Context.MODE_PRIVATE)
}
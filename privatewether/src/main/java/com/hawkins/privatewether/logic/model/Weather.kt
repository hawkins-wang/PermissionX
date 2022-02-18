package com.hawkins.privatewether.logic.model

data class Weather(val realTime: RealtimeResponse.RealTime, val daily: DailyResponse.Daily)

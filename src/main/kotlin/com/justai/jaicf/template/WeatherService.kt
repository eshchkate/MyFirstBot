package com.justai.jaicf.template

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request


class WeatherService {
    fun getDataWeather(city: String): WeatherResponse.WeatherData {
        val appId = "285db6364e319698c0fc9bb4ad6b8156"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$appId&lang=ru&units=metric")
            .get()
            .build()
        val response = client.newCall(request).execute()
        val gson = Gson()
        return gson.fromJson(response.body?.string(), WeatherResponse.WeatherData::class.java)
    }
}



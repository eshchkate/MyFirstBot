package com.justai.jaicf.template.service

import com.google.gson.Gson
import com.justai.jaicf.template.dto.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.Request


class WeatherService {
    fun getDataWeather(city: String): WeatherResponse.WeatherData {
        val appId = ""
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



package com.justai.jaicf.template.service

import com.google.gson.Gson
import com.justai.jaicf.template.dto.CityResponse

class CityService {
    private val gson = Gson()
    fun getCity(str: String?): String {
        return gson.fromJson(str.toString(), CityResponse::class.java).name
    }

    fun getLat(str: String?): Double {
        return gson.fromJson(str.toString(), CityResponse::class.java).lat
    }

    fun getLot(str: String?): Double {
        return gson.fromJson(str.toString(), CityResponse::class.java).lon
    }
}
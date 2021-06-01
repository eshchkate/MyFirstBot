package com.justai.jaicf.template.dto

import com.google.gson.annotations.SerializedName

data class CityResponse  (

    @SerializedName("name") var name : String,
    @SerializedName("lat") var lat : Double,
    @SerializedName("lon") var lon : Double,
    @SerializedName("country") var country : String,
    @SerializedName("timezone") var timezone : String,


    )
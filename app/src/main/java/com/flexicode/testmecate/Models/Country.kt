package com.flexicode.testmecate.Models

import com.google.gson.annotations.SerializedName

data class Country(

    @SerializedName("country_id"  ) var countryId   : String = "",
    @SerializedName("probability" ) var probability : Double = 0.0
)

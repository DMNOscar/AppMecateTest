package com.flexicode.testmecate.Models

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("count"   ) var count   : Int?               = null,
    @SerializedName("name"    ) var name    : String?            = null,
    @SerializedName("country" ) var country : ArrayList<Country> = arrayListOf()

)

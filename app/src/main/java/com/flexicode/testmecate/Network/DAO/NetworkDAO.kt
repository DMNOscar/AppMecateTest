package com.flexicode.testmecate.Network.DAO

import com.flexicode.testmecate.Models.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkDAO {

    @GET("/")
    fun searchtUser(@Query("name") name: String): Call<UserData>

}
package com.ict.useraplication.api


import com.ict.useraplication.model.CreateUser
import com.ict.useraplication.model.Data
import com.ict.useraplication.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ServiceAPI {
    @GET("users")
    fun getAllUsers() : Call<Data>

    @POST("users")
    fun createUser(@Body user: CreateUser): Call<CreateUser>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<ResponseBody>
}




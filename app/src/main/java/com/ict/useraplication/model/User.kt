package com.ict.useraplication.model


data class Data(val data : List<User>)

data class User(
    val email: String, val first_name: String, val last_name: String, val id: Int?, val avatar: String?)



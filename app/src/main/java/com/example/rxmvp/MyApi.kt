package com.example.rxmvp

import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("/posts")
    fun getPosts(): Call<List<Post>>
}
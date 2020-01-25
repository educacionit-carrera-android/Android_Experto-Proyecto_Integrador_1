package com.example.rxmvp.io.network

import com.example.rxmvp.data.Post
import io.reactivex.Single
import retrofit2.http.GET

interface MyApi {
    @GET("/posts")
    fun getPosts(): Single<List<Post>>
}
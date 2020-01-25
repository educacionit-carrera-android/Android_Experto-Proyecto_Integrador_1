package com.example.rxmvp.io

import com.example.rxmvp.data.Post

interface PostRepository {
    fun getPosts(
        onGetPostsSuccess: (posts: List<Post>) -> Unit,
        onGetPostsError: (errorMessage: String) -> Unit
    )
}
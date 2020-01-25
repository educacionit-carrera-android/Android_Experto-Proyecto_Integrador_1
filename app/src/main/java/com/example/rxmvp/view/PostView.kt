package com.example.rxmvp.view

import com.example.rxmvp.data.Post

interface PostView {
    fun showPosts(posts: List<Post>)
    fun showError(errorMessage: String)
}
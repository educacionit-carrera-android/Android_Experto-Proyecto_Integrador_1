package com.example.rxmvp.presenter

import com.example.rxmvp.io.PostRepository
import com.example.rxmvp.view.PostView

class PostPresenterImp(
    val postView: PostView,
    val postRepository: PostRepository
) : PostPresenter {

    override fun doGetPosts() {
        postRepository.getPosts(
            { posts -> postView.showPosts(posts) },
            { postView.showError(it) }
        )
    }

}
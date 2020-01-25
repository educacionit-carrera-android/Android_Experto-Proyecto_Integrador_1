package com.example.rxmvp.io

import com.example.rxmvp.data.Post
import com.example.rxmvp.io.network.MyApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostRepositoryImp(
    val myApi: MyApi,
    val compositeDisposable: CompositeDisposable
) : PostRepository {
    override fun getPosts(
        onGetPostsSuccess: (posts: List<Post>) -> Unit,
        onGetPostsError: (errorMessage: String) -> Unit
    ) {
        compositeDisposable.add(
            myApi
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { onGetPostsSuccess.invoke(it) },
                    {
                        it.message?.let {
                            onGetPostsError.invoke(it)
                        }
                    }
                )
        )
    }
}
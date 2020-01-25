package com.example.rxmvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    private lateinit var adapter: PostsAdapter
    private lateinit var api: MyApi
    private lateinit var rvPosts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecyclerViewPosts()
        initializeApi()
        retrievePosts()
    }

    private fun initializeRecyclerViewPosts() {
        rvPosts = findViewById(R.id.rvPosts)
        adapter = PostsAdapter()
        rvPosts.adapter = adapter
    }

    private fun initializeApi() {
        api = RetrofitClient.retrofit.create(MyApi::class.java)
    }

    private fun retrievePosts() {
        compositeDisposable.add(
            api.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { posts ->
                        posts?.let {
                            adapter.posts = posts
                            adapter.notifyDataSetChanged()
                        }
                    },
                    { Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_LONG).show() }

                )
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}

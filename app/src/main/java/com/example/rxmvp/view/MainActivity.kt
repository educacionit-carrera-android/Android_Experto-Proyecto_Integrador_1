package com.example.rxmvp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rxmvp.R
import com.example.rxmvp.data.Post
import com.example.rxmvp.io.PostRepositoryImp
import com.example.rxmvp.io.network.MyApi
import com.example.rxmvp.io.network.RetrofitClient
import com.example.rxmvp.presenter.PostPresenter
import com.example.rxmvp.presenter.PostPresenterImp
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity(), PostView {

    val compositeDisposable = CompositeDisposable()

    private lateinit var adapter: PostsAdapter
    private lateinit var rvPosts: RecyclerView
    private lateinit var presenter: PostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createPresenter()
        initializeRecyclerViewPosts()
        retrievePosts()
    }

    private fun createPresenter() {
        presenter = PostPresenterImp(
            this,
            PostRepositoryImp(
                RetrofitClient.retrofit.create(MyApi::class.java),
                compositeDisposable
            )
        )
    }

    private fun initializeRecyclerViewPosts() {
        rvPosts = findViewById(R.id.rvPosts)
        adapter = PostsAdapter()
        rvPosts.adapter = adapter
    }

    private fun retrievePosts() {
        presenter.doGetPosts()
    }

    override fun showPosts(posts: List<Post>) {
        adapter.posts = posts
        adapter.notifyDataSetChanged()
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}

package com.example.rxmvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

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
        val callGetPosts = api.getPosts()
        callGetPosts.enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                response.body()?.let {
                    adapter.posts = it
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}

package com.example.githubbrowser.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.adapters.CommitAdapter
import com.example.githubbrowser.adapters.RepositoryAdapter
import com.example.githubbrowser.databinding.ActivityCommitBinding
import com.example.githubbrowser.databinding.ActivityMainBinding
import com.example.githubbrowser.models.CommitResponse
import com.example.githubbrowser.models.Repository
import com.example.githubbrowser.networking.GithubApi
import com.example.githubbrowser.networking.RetrofitClient
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class CommitActivity : AppCompatActivity() {
    private lateinit var adapter: RepositoryAdapter
    private val commits = mutableListOf<CommitResponse>()
    private val binding: ActivityCommitBinding by lazy {
        ActivityCommitBinding.inflate(layoutInflater)
    }
    private lateinit var repository:Repository
    private lateinit var branchName:String
    private lateinit var githubApi:GithubApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        branchName = intent.getStringExtra("branchName").toString()
        repository = intent.getSerializableExtra("repo") as Repository
        setUp()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUp() {
        binding.tb.subtitle = branchName
        setSupportActionBar(binding.tb)
        githubApi = RetrofitClient.getRetrofitClient().create(GithubApi::class.java)
        val call = githubApi.getCommits(repository.owner!!,repository.name,branchName)
        call.enqueue(object : Callback<MutableList<CommitResponse>>{
            override fun onResponse(
                call: Call<MutableList<CommitResponse>>,
                response: Response<MutableList<CommitResponse>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val commits = response.body()
                    val adapter = CommitAdapter(commits!!,this@CommitActivity)
                    binding.rvCommits.layoutManager = LinearLayoutManager(this@CommitActivity)
                    binding.rvCommits.adapter = adapter
                    binding.rvCommits.visibility = View.VISIBLE
                    binding.pb.visibility = View.GONE
                } else {
                    Toast.makeText(
                        this@CommitActivity,
                        "Could not find your commits.Try again later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MutableList<CommitResponse>>, t: Throwable) {
                Toast.makeText(
                    this@CommitActivity,
                    "Please try again later!!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
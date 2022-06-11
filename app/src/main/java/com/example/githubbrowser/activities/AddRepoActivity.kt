package com.example.githubbrowser.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.githubbrowser.databinding.ActivityAddRepoBinding
import com.example.githubbrowser.models.Repository
import com.example.githubbrowser.models.RepositoryResponse
import com.example.githubbrowser.networking.GithubApi
import com.example.githubbrowser.networking.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class AddRepoActivity : AppCompatActivity() {
    private val TAG = "ADDREPO"
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("repoList", Context.MODE_PRIVATE)
    }
    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }
    private val binding: ActivityAddRepoBinding by lazy {
        ActivityAddRepoBinding.inflate(layoutInflater)
    }
    private val gson: Gson = Gson()
    private val githubApi: GithubApi by lazy {
        RetrofitClient.getRetrofitClient().create(GithubApi::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.btnAddNewRepo.visibility = View.GONE
            binding.pb.visibility = View.VISIBLE
        } else {
            binding.btnAddNewRepo.visibility = View.VISIBLE
            binding.pb.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setListeners() {
        setSupportActionBar(binding.tb)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.btnAddNewRepo.setOnClickListener {
            if (binding.etRepoOwner.text == null || binding.etRepoOwner.text!!.isEmpty()) {
                showToast("Name of owner/organization cannot be empty")
            } else if (binding.etRepoName.text == null || binding.etRepoName.text!!.isEmpty()) {
                showToast("Name of the repository cannot be empty")
            } else {
                loading(true)
                val repoOwner = binding.etRepoOwner.text!!.toString().trim()
                val repoName = binding.etRepoName.text!!.toString().trim()
                val call = githubApi.getRepository(repoOwner, repoName)
                call.enqueue(object : Callback<RepositoryResponse> {
                    override fun onResponse(
                        call: Call<RepositoryResponse>,
                        response: Response<RepositoryResponse>
                    ) {
                        loading(false)
                        if (response.isSuccessful && response.body() != null) {
                            val res = response.body()!!
                            val repo = Repository(
                                res.name,
                                res.description,
                                res.html_url,
                                repoOwner
                            )
                            Log.d(TAG, "onResponse: "+repo.owner)
                            var repoList = mutableListOf<Repository>()
                            if (sharedPreferences.contains("repoList")) {
                                val repoListJson = sharedPreferences.getString("repoList", "")
                                val type: Type =
                                    object : TypeToken<MutableList<Repository>>() {}.type
                                repoList =
                                    gson.fromJson(repoListJson, type)
                            }
                            repoList.add(repo)
                            editor.putString("repoList", gson.toJson(repoList))
                            editor.commit()
                            finish()
                        } else {
                            Toast.makeText(
                                this@AddRepoActivity,
                                "Could not find your repository. Make sure it is not private",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RepositoryResponse>, t: Throwable) {
                        loading(false)
                        Log.d(TAG, "onFailure: " + t.message)
                        Toast.makeText(
                            this@AddRepoActivity,
                            "Please try again later!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }
}
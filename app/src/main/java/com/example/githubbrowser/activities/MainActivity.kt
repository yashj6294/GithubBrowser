package com.example.githubbrowser.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.adapters.RepositoryAdapter
import com.example.githubbrowser.databinding.ActivityMainBinding
import com.example.githubbrowser.models.Repository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainActivity : AppCompatActivity(){
    private val sharedPreferences:SharedPreferences by lazy {
        getSharedPreferences("repoList", Context.MODE_PRIVATE)
    }
    private lateinit var adapter:RepositoryAdapter
    private val repositories = mutableListOf<Repository>()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var gson:Gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUp()
        setListeners()
    }

    private fun setUp() {
        setSupportActionBar(binding.tb)
        binding.rvRepositories.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.rvRepositories.layoutManager = LinearLayoutManager(this)
        adapter = RepositoryAdapter(repositories,this)
        binding.rvRepositories.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if(sharedPreferences.contains("repoList")){
            val repoListJson = sharedPreferences.getString("repoList","")
            val type: Type = object : TypeToken<ArrayList<Repository>>() {}.type
            binding.tvTrackRepo.visibility = View.GONE
            binding.btnAddNewRepo.visibility = View.GONE
            val repoList = gson.fromJson<MutableList<Repository>>(repoListJson,type)
            repositories.clear()
            repositories.addAll(repoList)
            adapter.notifyDataSetChanged()
            binding.rvRepositories.visibility = View.VISIBLE
        }
    }

    private fun setListeners() {
        binding.btnAddNewRepo.setOnClickListener {
            val intent = Intent(this@MainActivity,AddRepoActivity::class.java)
            startActivity(intent)
        }
        binding.icAddNewRepo.setOnClickListener {
            val intent = Intent(this@MainActivity,AddRepoActivity::class.java)
            startActivity(intent)
        }
    }
}
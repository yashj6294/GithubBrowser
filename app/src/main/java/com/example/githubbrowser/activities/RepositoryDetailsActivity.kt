package com.example.githubbrowser.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.githubbrowser.adapters.ViewPagerAdapter
import com.example.githubbrowser.databinding.ActivityAddRepoBinding
import com.example.githubbrowser.databinding.ActivityRepositoryDetailsBinding
import com.example.githubbrowser.models.Repository
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class RepositoryDetailsActivity : FragmentActivity() {
    private val binding: ActivityRepositoryDetailsBinding by lazy {
        ActivityRepositoryDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var repository: Repository
    private var pos:Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        repository = intent.getSerializableExtra("repo") as Repository
        pos = intent.getIntExtra("position",0)
        setUp()
    }

    private fun setUp() {
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.icView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repository.html_url)
            startActivity(intent)
        }
        binding.icDeleteRepo.setOnClickListener {
            val prefs = getSharedPreferences("repoList",Context.MODE_PRIVATE)
            val editor = prefs.edit()
            val repoListJson = prefs.getString("repoList", "")
            val type: Type =
                object : TypeToken<MutableList<Repository>>() {}.type
            val repoList =
                Gson().fromJson<MutableList<Repository>>(repoListJson, type)
            repoList.removeAt(pos)
            editor.putString("repoList", Gson().toJson(repoList))
            editor.apply()
            finish()
        }
        binding.tvRepoName.text = repository.name
        binding.tvRepoDescription.text = repository.description
        binding.viewPager.adapter = ViewPagerAdapter(this,repository)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if(position==0) tab.text="Branches"
            else tab.text="Issues"
        }.attach()
    }
}
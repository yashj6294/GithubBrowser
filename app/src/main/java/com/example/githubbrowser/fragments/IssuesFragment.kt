package com.example.githubbrowser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.adapters.BranchAdapter
import com.example.githubbrowser.adapters.IssueAdapter
import com.example.githubbrowser.databinding.FragmentBranchesBinding
import com.example.githubbrowser.databinding.FragmentIssuesBinding
import com.example.githubbrowser.models.Branch
import com.example.githubbrowser.models.Issue
import com.example.githubbrowser.models.Repository
import com.example.githubbrowser.networking.GithubApi
import com.example.githubbrowser.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IssuesFragment(private val repository: Repository) : Fragment() {
    private lateinit var binding: FragmentIssuesBinding
    private lateinit var githubApi: GithubApi
    private var branches:MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubApi = RetrofitClient.getRetrofitClient().create(GithubApi::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentIssuesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val call = githubApi.getIssues(repository.owner!!,repository.name)
        call.enqueue(object : Callback<MutableList<Issue>>{
            override fun onResponse(
                call: Call<MutableList<Issue>>,
                response: Response<MutableList<Issue>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val issuesList = response.body()!!
                    binding.pb.visibility = View.GONE
                    if(issuesList.size==0){
                        binding.tvNoIssues.visibility = View.VISIBLE
                        return
                    }
                    val adapter = IssueAdapter(issuesList, activity!!)
                    binding.rvIssues.adapter = adapter
                    binding.rvIssues.layoutManager = LinearLayoutManager(activity)
                    binding.rvIssues.visibility = View.VISIBLE
                }else {
                    Toast.makeText(
                        activity,
                        "Could not find your repository. Make sure it is not private",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<MutableList<Issue>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Could not find your issues.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
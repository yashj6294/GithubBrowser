package com.example.githubbrowser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.adapters.BranchAdapter
import com.example.githubbrowser.databinding.FragmentBranchesBinding
import com.example.githubbrowser.models.Branch
import com.example.githubbrowser.models.Repository
import com.example.githubbrowser.networking.GithubApi
import com.example.githubbrowser.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BranchesFragment(private val repository: Repository) : Fragment() {
    private lateinit var binding: FragmentBranchesBinding
    private lateinit var githubApi: GithubApi
    private var branches:MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubApi = RetrofitClient.getRetrofitClient().create(GithubApi::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentBranchesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: "+repository.owner)
        val call = githubApi.getBranches(repository.owner!!,repository.name)
        call.enqueue(object : Callback<MutableList<Branch>> {
            override fun onResponse(
                call: Call<MutableList<Branch>>,
                response: Response<MutableList<Branch>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    for(i in response.body()!!){
                        branches.add(i.name)
                    }
                    binding.rvBranches.layoutManager = LinearLayoutManager(activity)
                    binding.rvBranches.adapter = BranchAdapter(branches,activity!!,repository)
                    binding.pb.visibility = View.GONE
                    binding.rvBranches.visibility = View.VISIBLE
                }else {
                    Toast.makeText(
                        activity,
                        "Could not find your repository. Make sure it is not private",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Branch>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Could not find your branches.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}
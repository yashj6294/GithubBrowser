package com.example.githubbrowser.networking

import com.example.githubbrowser.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("repos/{owner}/{repo}")
    fun getRepository(@Path("owner",encoded = true) owner:String, @Path("repo", encoded = true) repo:String) : Call<RepositoryResponse>

    @GET("repos/{owner}/{repo}/branches")
    fun getBranches(@Path("owner",encoded = true) owner:String, @Path("repo", encoded = true) repo:String) : Call<MutableList<Branch>>

    @GET("repos/{owner}/{repo}/issues?state=open")
    fun getIssues(@Path("owner",encoded = true) owner:String, @Path("repo", encoded = true) repo:String) : Call<MutableList<Issue>>

    @GET("repos/{owner}/{repo}/commits")
    fun getCommits(@Path("owner",encoded = true) owner:String, @Path("repo", encoded = true) repo:String,@Query("sha") branchName:String) : Call<MutableList<CommitResponse>>

}
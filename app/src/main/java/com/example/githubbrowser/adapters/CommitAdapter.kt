package com.example.githubbrowser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubbrowser.databinding.CommitLayoutBinding
import com.example.githubbrowser.models.CommitResponse

class CommitAdapter(private val commitList:MutableList<CommitResponse>,private val context: Context) : RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    inner class CommitViewHolder(private val binding:CommitLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(commit:CommitResponse){
            Glide.with(context).load(commit.author.avatar_url).into(binding.ivCommitter)
            binding.tvCommitMessage.text = commit.commit.message
            binding.tvCommitter.text = commit.author.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        return CommitViewHolder(
            CommitLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        holder.bind(commitList[position])
    }

    override fun getItemCount(): Int {
        return commitList.size
    }
}
package com.example.githubbrowser.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubbrowser.databinding.FragmentIssuesBinding
import com.example.githubbrowser.databinding.IssueLayoutBinding
import com.example.githubbrowser.models.Issue

class IssueAdapter(private val issues : MutableList<Issue>,private val activity:Activity) : RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    inner class IssueViewHolder(private val binding:IssueLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(issue: Issue){
            binding.tvIssueTitle.text = issue.title
            binding.tvIssueCreatorName.text = issue.user.login
            Glide.with(activity).load(issue.user.avatar_url).into(binding.ivIssueCreatorAvatar);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        return IssueViewHolder(
            IssueLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(issues[position])
    }

    override fun getItemCount(): Int {
        return issues.size
    }


}
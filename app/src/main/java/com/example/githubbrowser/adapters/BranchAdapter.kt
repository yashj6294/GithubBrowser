package com.example.githubbrowser.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.activities.CommitActivity
import com.example.githubbrowser.databinding.BranchLayoutBinding
import com.example.githubbrowser.models.Repository

class BranchAdapter(private val branches: MutableList<String>, private val context: Context,private val repository: Repository) :
    RecyclerView.Adapter<BranchAdapter.BranchViewHolder>() {

    inner class BranchViewHolder(private val binding: BranchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String,position: Int) {
            binding.tvBranchName.text = name
            binding.root.setOnClickListener {
                val intent = Intent(context, CommitActivity::class.java)
                intent.putExtra("branchName",branches[position])
                intent.putExtra("repo",repository)
                startActivity(context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchViewHolder {
        return BranchViewHolder(
            BranchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BranchViewHolder, position: Int) {
        holder.bind(branches[position],position)
    }

    override fun getItemCount(): Int {
        return branches.size
    }
}
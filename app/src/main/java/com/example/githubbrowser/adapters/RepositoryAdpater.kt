package com.example.githubbrowser.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.activities.RepositoryDetailsActivity
import com.example.githubbrowser.databinding.RepositoryLayoutBinding
import com.example.githubbrowser.models.Repository


class RepositoryAdapter(private val reposList: MutableList<Repository>, private val context: Context) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    inner class RepositoryViewHolder(private val itemBinding: RepositoryLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(repository: Repository, context: Context) {
            itemBinding.tvRepoName.text = repository.name
            itemBinding.tvRepoDescription.text = repository.description
            itemBinding.icShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                val shareBody =
                    "Hey!! , checkout this github repository I found.\nName : ${repository.name}\nDescription : ${repository.description}\nURL : ${repository.html_url}"
                intent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(
                    context,
                    intent,null
                )
            }
            itemBinding.root.setOnClickListener{
                val intent = Intent(context,RepositoryDetailsActivity::class.java)
                intent.putExtra("repo",repository)
                intent.putExtra("position",adapterPosition)
                startActivity(context,intent,null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            RepositoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(reposList[position], context)
    }

    override fun getItemCount(): Int {
        return reposList.size
    }
}
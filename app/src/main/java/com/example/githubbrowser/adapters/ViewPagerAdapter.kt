package com.example.githubbrowser.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubbrowser.fragments.BranchesFragment
import com.example.githubbrowser.fragments.IssuesFragment
import com.example.githubbrowser.models.Repository

class ViewPagerAdapter(fa:FragmentActivity, private val repository: Repository) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0){
            BranchesFragment(repository)
        }else{
            IssuesFragment(repository)
        }
    }
}
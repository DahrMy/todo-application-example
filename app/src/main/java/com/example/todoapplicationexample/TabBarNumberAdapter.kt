package com.example.todoapplicationexample

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todoapplicationexample.lists.TasksInProgressFragment

class TabBarNumberAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 1
    override fun createFragment(position: Int): Fragment {
        val fragmentTasksInProgressFragment = TasksInProgressFragment.newInstance()

        return when(position) {
            1 -> TasksInProgressFragment()
            else -> {TasksInProgressFragment()}
        }

    }


}
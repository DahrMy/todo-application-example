package com.example.todoapplicationexample.todo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todoapplicationexample.todo.lists.TasksDeletedFragment
import com.example.todoapplicationexample.todo.lists.TasksDoneFragment
import com.example.todoapplicationexample.todo.lists.TasksInProgressFragment

class TabBarNumberAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3
    override fun createFragment(position: Int): Fragment {
        val fragmentTasksInProgressFragment = TasksInProgressFragment.newInstance() // TODO: QUESTION(why we need it?)

        return when(position) {
            0 -> TasksInProgressFragment()
            1 -> TasksDoneFragment()
            2 -> TasksDeletedFragment()
            else -> TasksInProgressFragment()
        }

    }


}
package com.example.todoapplicationexample.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapplicationexample.databinding.FragmentTodoSectionBinding
import com.google.android.material.tabs.TabLayoutMediator

class TodoSectionFragment : Fragment() {

    private var _binding: FragmentTodoSectionBinding? = null
    private val binding get() = _binding!!


    private lateinit var tabLayoutAdapter: TabBarNumberAdapter

    private lateinit var tabNames: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSectionBinding.inflate(inflater, container,false)

        tabNames = getTabNames()
        tabLayoutAdapter = TabBarNumberAdapter(requireActivity())
        binding.apply {
//            viewPager.adapter = tabLayoutAdapter
//            TabLayoutMediator(tabLayout, viewPager) {
//                tab, position -> tab.text = tabNames[position]
//            }.attach()

            fabAddTask.setOnClickListener { // TODO: Move from here
                groupAddTask.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    private fun getTabNames(): List<String> {
        return listOf(
            TaskStatus.IN_PROGRESS.s,
            TaskStatus.DONE.s,
            TaskStatus.DELETED.s
        )
    }

}
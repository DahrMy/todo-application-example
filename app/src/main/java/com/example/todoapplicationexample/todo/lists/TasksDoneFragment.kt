package com.example.todoapplicationexample.todo.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplicationexample.databinding.FragmentTasksDoneListBinding
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.TaskUtils

class TasksDoneFragment : Fragment() {

    private var _binding: FragmentTasksDoneListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TasksInProgressRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksDoneListBinding.inflate(inflater, container, false)

        val view = binding.root
        val recyclerView = binding.recyclerView

        val list = TaskUtils.generateSimpleList().filter {
            it.status == TaskStatus.DONE
        }
        adapter = TasksInProgressRecyclerViewAdapter(list.toMutableList())

        LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TasksDoneFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
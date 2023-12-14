package com.example.todoapplicationexample.todo.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.TaskUtils
import com.example.todoapplicationexample.databinding.FragmentTasksInProgressListBinding

class TasksInProgressFragment : Fragment() {

    private var _binding: FragmentTasksInProgressListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TasksInProgressRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksInProgressListBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.list

        val list = TaskUtils.generateSimpleList().filter {
            it.status == TaskStatus.IN_PROGRESS
        }
        adapter = TasksInProgressRecyclerViewAdapter(list)

        LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() {}
    }
}
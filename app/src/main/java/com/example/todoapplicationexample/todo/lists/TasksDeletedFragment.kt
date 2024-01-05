package com.example.todoapplicationexample.todo.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapplicationexample.databinding.FragmentTasksDeletedBinding
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.TaskUtils

class TasksDeletedFragment : Fragment() {

    private var _binding: FragmentTasksDeletedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TasksInProgressRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksDeletedBinding.inflate(inflater, container, false)

        val view = binding.root
        val recyclerView = binding.list

        val list = TaskUtils.generateSimpleList().filter {
            it.status == TaskStatus.DELETED
        }
        adapter = TasksInProgressRecyclerViewAdapter(list)

        LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

}
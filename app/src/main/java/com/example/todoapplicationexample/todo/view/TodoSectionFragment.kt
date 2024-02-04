package com.example.todoapplicationexample.todo.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todoapplicationexample.databinding.FragmentTodoSectionBinding
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.viewmodel.TasksListModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModelFactory
import kotlinx.coroutines.launch

class TodoSectionFragment : Fragment() {

    private var _binding: FragmentTodoSectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var list: List<Task> // TODO: Move to model
    private val recyclerViewAdapter by lazy { TasksRecyclerViewAdapter() }
    private val viewModel by lazy { initViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSectionBinding.inflate(inflater, container,false)

        binding.apply {

            recyclerViewTasks.adapter = recyclerViewAdapter

            fabAddTask.setOnClickListener {
                groupAddTask.visibility = View.VISIBLE
            }

            initListFlow()
            viewModel.loadList(TaskStatus.IN_PROGRESS)

        }

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initListFlow() {
        lifecycleScope.launch {
            viewModel.getListFlow().collect {
                list = it.toMutableList()
                recyclerViewAdapter.updateList(list)
            }
        }
    }

    private fun initViewModel() = ViewModelProvider(
        this, TasksListViewModelFactory(TasksListModel())
    )[TasksListViewModel::class.java]

}
package com.example.todoapplicationexample.todo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todoapplicationexample.databinding.FragmentTodoSectionBinding
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.viewmodel.TasksListModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModelFactory
import kotlinx.coroutines.launch

class TodoSectionFragment : Fragment() {

    private var _binding: FragmentTodoSectionBinding? = null
    private val binding get() = _binding!!
    private val recyclerViewAdapter by lazy { TasksRecyclerViewAdapter() }
    private val viewModel by lazy { initViewModel(TasksListModel()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSectionBinding.inflate(inflater, container,false)
        binding.recyclerViewTasks.adapter = recyclerViewAdapter

        initObservers()
        setOnClickListeners()

        viewModel.getTasksList(TaskStatus.IN_PROGRESS)

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initViewModel(model: TasksListModel) = ViewModelProvider(
        this, TasksListViewModelFactory(model)
    )[TasksListViewModel::class.java]

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.getListFlow().collect {
                recyclerViewAdapter.updateList(it)
            }
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            fabAddTask.setOnClickListener {
                groupAddTask.visibility = View.VISIBLE
            }
        }
    }

}
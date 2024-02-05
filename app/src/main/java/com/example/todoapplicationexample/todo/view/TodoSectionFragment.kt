package com.example.todoapplicationexample.todo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todoapplicationexample.R
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

    private var taskStatusFilter = TaskStatus.IN_PROGRESS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSectionBinding.inflate(inflater, container,false)
        binding.recyclerViewTasks.adapter = recyclerViewAdapter

        initObservers()
        viewModel.emitTasksList(taskStatusFilter)

        setViewContent(taskStatusFilter)
        setOnClickListeners()


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

    private fun setViewContent(taskStatus: TaskStatus) {
        binding.apply {
            textviewOpenTaskStatusFilterMenu.text = String.format(
                resources.getString(R.string.textview_open_task_status_filter_menu_text),
                taskStatus.text
            )
        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            textviewOpenTaskStatusFilterMenu.setOnClickListener { view ->
                textViewOpenTaskStatusFilterMenuOnClickListener(view)
            }
        }
    }

    private fun textViewOpenTaskStatusFilterMenuOnClickListener(view: View) {
        showMenu(view, R.menu.popup_menu_task_status_filter)
    }

    private fun popupMenuTaskStatusFilterOnClickListener(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.in_progress -> {
                taskStatusFilter = TaskStatus.IN_PROGRESS
                setViewContent(taskStatusFilter)
                return true
            }
            R.id.done -> {
                taskStatusFilter = TaskStatus.DONE
                setViewContent(taskStatusFilter)
                return true
            }
            R.id.deleted -> {
                taskStatusFilter = TaskStatus.DELETED
                setViewContent(taskStatusFilter)
                return true
            }
        }
        return false
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) { // TODO: separate logically
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { popupMenuTaskStatusFilterOnClickListener(it) }

        popup.show()
    }

}
package com.example.todoapplicationexample.todo.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.todoapplicationexample.R
import com.example.todoapplicationexample.databinding.FragmentTodoSectionBinding
import com.example.todoapplicationexample.db.todo.TodoDataBase
import com.example.todoapplicationexample.db.todo.tables.tasks.TasksDao
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.viewmodel.TasksListModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModelFactory
import kotlinx.coroutines.launch

class TodoSectionFragment : Fragment() {

    private var _binding: FragmentTodoSectionBinding? = null
    private val binding get() = _binding!!
    private val recyclerViewAdapter by lazy { TasksRecyclerViewAdapter() }

    private lateinit var database: TodoDataBase
    private lateinit var tasksDao: TasksDao

    private val statusFilterLiveData by lazy { MutableLiveData(TaskStatus.IN_PROGRESS) }

    private val viewModel by lazy { initViewModel(TasksListModel(tasksDao)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSectionBinding.inflate(inflater, container,false)
        binding.recyclerViewTasks.adapter = recyclerViewAdapter
        binding.textInputLayoutAddTask.isEndIconVisible = false

        initDB()
        initObservers()
        viewModel.startListLoading()
        setViewListeners()

        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initDB() { // TODO: Move to MyApplication.kt
        database = Room.databaseBuilder(
            requireContext(), TodoDataBase::class.java, "TODO_Database"
        ).build()

        tasksDao = database.tasksDao()
    }

    private fun initViewModel(model: TasksListModel) = ViewModelProvider(
        this, TasksListViewModelFactory(model)
    )[TasksListViewModel::class.java]

    private fun initObservers() {

        lifecycleScope.launch {
            viewModel.tasksFlow.collect { list ->
                statusFilterLiveData.observe(viewLifecycleOwner) { status ->
                    val result = list.filter { it.status == status }
                    recyclerViewAdapter.updateList(result) // TODO: Update all list (not an one item). Fix it.
                }
            }
        }

        lifecycleScope.launch {
            statusFilterLiveData.observe(viewLifecycleOwner) { status ->
                binding.textviewOpenTaskStatusFilterMenu.text = String.format(
                    resources.getString(R.string.textview_open_task_status_filter_menu_text),
                    status.text
                )
            }
        }

    }

    private fun setViewListeners() {
        binding.apply {

            textviewOpenTaskStatusFilterMenu.setOnClickListener { view ->
                showMenu(view, R.menu.popup_menu_task_status_filter)
            }

            textInputLayoutAddTask.setEndIconOnClickListener {
                textInputLayoutAddTaskEndIconOnClickListener()
            }

            editTextAddTask.addTextChangedListener(editTextAddTaskTextWatcher())

        }
    }

    private fun textInputLayoutAddTaskEndIconOnClickListener() {
        binding.apply {
            val task = Task(editTextAddTask.text.toString(), TaskStatus.IN_PROGRESS)
            viewModel.uploadItem(task)
            editTextAddTask.setText("")
            recyclerViewTasks.smoothScrollToPosition(0)
        }
    }

    private fun editTextAddTaskTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                val isEditTextNotEmpty = s.toString().isNotEmpty()
                binding.textInputLayoutAddTask.isEndIconVisible = isEditTextNotEmpty
            }

        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setOnMenuItemClickListener { popupMenuTaskStatusFilterOnClickListener(it) }
        popup.show()
    }

    private fun popupMenuTaskStatusFilterOnClickListener(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.in_progress -> {
                statusFilterLiveData.postValue(TaskStatus.IN_PROGRESS)
                return true
            }
            R.id.done -> {
                statusFilterLiveData.postValue(TaskStatus.DONE)
                return true
            }
            R.id.deleted -> {
                statusFilterLiveData.postValue(TaskStatus.DELETED)
                return true
            }
            else -> false
        }
    }

}
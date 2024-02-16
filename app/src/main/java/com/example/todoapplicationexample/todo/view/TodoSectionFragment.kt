package com.example.todoapplicationexample.todo.view

import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapplicationexample.R
import com.example.todoapplicationexample.databinding.FragmentTodoSectionBinding
import com.example.todoapplicationexample.db.todo.TodoDataBase
import com.example.todoapplicationexample.db.todo.tables.tasks.TasksDao
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.todo.viewmodel.TasksListModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModel
import com.example.todoapplicationexample.todo.viewmodel.TasksListViewModelFactory
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

// TODO: Create cancellation reminder: https://stackoverflow.com/questions/28922521/how-to-cancel-alarm-from-alarmmanager
class TodoSectionFragment : Fragment() {

    private var _binding: FragmentTodoSectionBinding? = null
    private val binding get() = _binding!!

    private val recyclerViewAdapter by lazy { TasksRecyclerViewAdapter() }

    private lateinit var database: TodoDataBase
    private lateinit var tasksDao: TasksDao

    private val viewModel by lazy { initViewModel(TasksListModel(requireContext(), tasksDao)) }

    private val datePicker by lazy { initDatePicker() }
    private val timePicker by lazy { initTimePicker() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoSectionBinding.inflate(inflater, container,false)

        initDB()
        initViews()
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
        val migration1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks_table ADD COLUMN remindTime INTEGER DEFAULT null")
            }
        }

        database = Room
            .databaseBuilder(requireContext(), TodoDataBase::class.java, "TODO_Database")
            .addMigrations(migration1To2)
            .build()

        tasksDao = database.tasksDao()
    }

    private fun initViewModel(model: TasksListModel) = ViewModelProvider(
        this, TasksListViewModelFactory(model)
    )[TasksListViewModel::class.java]

    private fun initViews() {
        binding.apply {
            recyclerViewTasks.adapter = recyclerViewAdapter
            textInputLayoutAddTask.isEndIconVisible = false
        }
    }

    private fun initObservers() {

        lifecycleScope.launch {
            viewModel.tasksFlow.collect { list ->
                viewModel.statusFilterLiveData.observe(viewLifecycleOwner) { status ->
                    val result = list.filter { it.status == status }
                    recyclerViewAdapter.updateList(result) // TODO: Update all list (not an one item). Fix it.
                }
            }
        }

        lifecycleScope.launch {
            viewModel.statusFilterLiveData.observe(viewLifecycleOwner) { status ->
                binding.textviewOpenTaskStatusFilterMenu.text = String.format(
                    resources.getString(R.string.textview_open_task_status_filter_menu_text),
                    status.text
                )
            }
        }

    }

    private fun initDatePicker(): MaterialDatePicker<Long> {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())

        return MaterialDatePicker.Builder.datePicker()
            .setTitleText(resources.getString(R.string.date_picker_title))
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    private fun initTimePicker(): MaterialTimePicker {
        val currentTime = Calendar.getInstance()
        val timeFormat = if (DateFormat.is24HourFormat(requireContext()))
            TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        return MaterialTimePicker.Builder()
            .setTimeFormat(timeFormat)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .setHour(currentTime.get(Calendar.HOUR_OF_DAY))
            .setMinute(currentTime.get(Calendar.MINUTE))
            .setTitleText(resources.getString(R.string.time_picker_title))
            .build()
    }

    private fun setViewListeners() {
        binding.apply {

            textviewOpenTaskStatusFilterMenu.setOnClickListener { view ->
                showMenu(view, R.menu.popup_menu_task_status_filter)
            }

            buttonSetReminder.setOnClickListener {
                datePicker.show(parentFragmentManager, tag)
            }

            buttonEditReminder.setOnClickListener {
                datePicker.show(parentFragmentManager, tag) // TODO: Change action
            }

            datePicker.addOnPositiveButtonClickListener { time ->
                viewModel.timeToRemind = time
                println(time)
                timePicker.show(parentFragmentManager, tag)
            }

            timePicker.addOnPositiveButtonClickListener {
                var timeInMills: Long = 0
                timeInMills += TimeUnit.HOURS.toMillis(timePicker.hour.toLong())
                timeInMills += TimeUnit.MINUTES.toMillis(timePicker.minute.toLong())
                viewModel.timeToRemind = viewModel.timeToRemind!! + timeInMills

                buttonSetReminder.visibility = View.GONE
                buttonEditReminder.visibility = View.VISIBLE
            }

            textInputLayoutAddTask.setEndIconOnClickListener {
                textInputLayoutAddTaskEndIconOnClickListener()
            }

            editTextAddTask.addTextChangedListener(editTextAddTaskTextWatcher())

        }
    }

    private fun textInputLayoutAddTaskEndIconOnClickListener() {
        binding.apply {
            val task =
                Task(null, editTextAddTask.text.toString(), TaskStatus.IN_PROGRESS, null)
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
                viewModel.statusFilterLiveData.postValue(TaskStatus.IN_PROGRESS)
                return true
            }
            R.id.done -> {
                viewModel.statusFilterLiveData.postValue(TaskStatus.DONE)
                return true
            }
            R.id.deleted -> {
                viewModel.statusFilterLiveData.postValue(TaskStatus.DELETED)
                return true
            }
            else -> false
        }
    }

}
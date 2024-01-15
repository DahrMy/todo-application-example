package com.example.todoapplicationexample.todo.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.databinding.FragmentTasksInProgressListBinding
import com.example.todoapplicationexample.todo.Task
import io.reactivex.rxjava3.disposables.CompositeDisposable

class TasksInProgressFragment : Fragment() {

    private var _binding: FragmentTasksInProgressListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TasksInProgressRecyclerViewAdapter
    private lateinit var viewModel: TasksListViewModel
    private lateinit var list: MutableList<Task>
    private val compositeDisposable = CompositeDisposable()
    private val model = TasksListModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksInProgressListBinding.inflate(inflater, container, false)
        val view = binding.root
        list = emptyList<Task>().toMutableList()
        adapter = TasksInProgressRecyclerViewAdapter(list)
        LinearLayoutManager(context)

        viewModel = initViewModel()
        viewModel.loadList(TaskStatus.IN_PROGRESS)
        showList()

        return view
    }

    override fun onDestroyView() {
        _binding = null
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun initViewModel() = ViewModelProvider(
        this, TasksListViewModelFactory(model, compositeDisposable)
    )[TasksListViewModel::class.java]

    private fun showList() {
        val disposable = viewModel.getListObservable().subscribe { tasks ->
            list = tasks.toMutableList()
            adapter.updateList(list)
            binding.recyclerView.adapter = adapter
        }
        compositeDisposable.add(disposable)
    }

}
package com.example.todoapplicationexample.todo.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todoapplicationexample.todo.TaskStatus
import com.example.todoapplicationexample.databinding.FragmentTasksInProgressListBinding
import com.example.todoapplicationexample.todo.Task
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch

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
        binding.recyclerView.adapter = adapter

        viewModel = initViewModel()
        initListFlow()
        viewModel.loadList(TaskStatus.IN_PROGRESS)

/*        val exceptionHandler = CoroutineExceptionHandler { _, throwable -> }
//        runBlocking {
//            launch(exceptionHandler) {  }
//
//            val result = async {  }
//
//            val job: Job = launch {  }
//
//            job.invokeOnCompletion {  }
//
//         }
*/

        return view
    }

    override fun onDestroyView() {
        _binding = null
        compositeDisposable.clear()
        super.onDestroyView()
    }

    private fun initViewModel() = ViewModelProvider(
        this, TasksListViewModelFactory(model)
    )[TasksListViewModel::class.java]

    private fun initListFlow() {
        lifecycleScope.launch {
            viewModel.getListFlow().collect {
                list = it.toMutableList()
                adapter.updateList(list)
            }
        }
    }

}
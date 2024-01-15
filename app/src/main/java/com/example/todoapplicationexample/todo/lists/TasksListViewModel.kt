package com.example.todoapplicationexample.todo.lists

import androidx.lifecycle.ViewModel
import com.example.todoapplicationexample.todo.Task
import com.example.todoapplicationexample.todo.TaskStatus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class TasksListViewModel(
    private val model: TasksListModel,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val tasksSubject: PublishSubject<List<Task>> = PublishSubject.create()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun getListObservable(): Observable<List<Task>> = tasksSubject

    fun loadList(status: TaskStatus) {

        val tasksListDisposable = model.getTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                tasksSubject.onNext(result.filter {
                    it.status == status
                })
                getListObservable()
            }

        disposable.add(tasksListDisposable)

    }

}
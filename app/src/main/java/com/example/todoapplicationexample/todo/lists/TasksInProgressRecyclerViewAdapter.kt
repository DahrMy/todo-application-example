package com.example.todoapplicationexample.todo.lists

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.example.todoapplicationexample.todo.Task

import com.example.todoapplicationexample.databinding.ItemTaskInProgressBinding

class TasksInProgressRecyclerViewAdapter(
    private val values: MutableList<Task>
) : RecyclerView.Adapter<TasksInProgressRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTaskInProgressBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
    }

    override fun getItemCount(): Int = values.size

    fun updateList(newValues: List<Task>) {
        val diffCallback = TasksInProgressRecyclerViewCallback(values, newValues)
        val diffNotes = DiffUtil.calculateDiff(diffCallback)
        values.clear()
        values.addAll(newValues)
        diffNotes.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(binding: ItemTaskInProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.textviewTaskName
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}

class TasksInProgressRecyclerViewCallback(
    private val oldList: List<Task>,
    private val newList: List<Task>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name &&
                oldList[oldItemPosition].status == newList[newItemPosition].status
    }
}
package com.example.todoapplicationexample.todo.lists

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.todoapplicationexample.todo.Task

import com.example.todoapplicationexample.databinding.ItemTaskInProgressBinding

class TasksInProgressRecyclerViewAdapter(
    private val values: List<Task>
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

    inner class ViewHolder(binding: ItemTaskInProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.textviewTaskName
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}
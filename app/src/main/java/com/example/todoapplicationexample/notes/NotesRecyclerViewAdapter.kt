package com.example.todoapplicationexample.notes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.todoapplicationexample.databinding.ItemNoteBinding

class NotesRecyclerViewAdapter(
    private val values: List<Note>
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.textviewNoteTitle.text = item.title
        holder.textviewNoteText.text = item.text
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        val textviewNoteTitle: TextView = binding.textviewNoteTitle
        val textviewNoteText: TextView = binding.textviewNoteText
    }

}
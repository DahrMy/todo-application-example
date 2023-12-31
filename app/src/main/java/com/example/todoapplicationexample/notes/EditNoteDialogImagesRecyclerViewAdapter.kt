package com.example.todoapplicationexample.notes

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplicationexample.databinding.ItemNoteEditorImagesBinding

class EditNoteDialogImagesRecyclerViewAdapter(
    private val values: MutableList<Drawable>
) : RecyclerView.Adapter<EditNoteDialogImagesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoteEditorImagesBinding.inflate(
            LayoutInflater.from(parent.context), parent,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.setImageDrawable(item)
    }

    fun updateList(newValues: List<Drawable>) {
        val diffCallback = editNoteDialogImagesRecyclerViewCallback(values, newValues)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        values.clear()
        values.addAll(newValues)
        diffCourses.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemNoteEditorImagesBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: ImageView = binding.imageview
    }

}

class editNoteDialogImagesRecyclerViewCallback(
    private val oldList: List<Drawable>,
    private val newList: List<Drawable>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList.get(newItemPosition) // TODO: question(Is it good use === operator? (source: https://www.geeksforgeeks.org/diffutil-in-recyclerview-in-android/#:~:text=These%20techniques%20are%20significantly%20more%20efficient%20than%20notifyDataSetChanged().%20However%2C%20in%20order%20for%20DiffUtils%20to%20function%20in%20the%20project%2C%20we%20must%20give%20information%20about%20the%20old%20and%20new%20lists.%20DiffUtil%20is%20used%20for%20this.%20Request%20a%20callback.%20We%E2%80%99ll%20make%20a%20class.) )
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition] // TODO: question(Is it a good way to search differences in drawable)
    }
}

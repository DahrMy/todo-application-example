package com.example.todoapplicationexample.notes

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapplicationexample.Constants
import com.example.todoapplicationexample.R
import com.example.todoapplicationexample.databinding.FragmentNotesListBinding
import com.example.todoapplicationexample.notes.Note.Companion.generateSimpleList

class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 2
    private lateinit var list: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.recyclerviewNotes
        list = generateSimpleList(5).toMutableList()
        getParcelableNote()?.let { list.add(0, it) }

        binding.fabCreateNote.setOnClickListener { openDialogCreateNewNote() }

        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = NotesRecyclerViewAdapter(list)
        }

        return view
    }

    private fun openDialogCreateNewNote() {
        parentFragmentManager
            .beginTransaction()
            .addToBackStack("")
            .replace(R.id.fragment_container_view, EditNoteDialogFragment())
            .commit()
    }

    private fun getParcelableNote(): Note? {
        val bundle = arguments
        val note = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle?.let { it.getSerializable(Constants.NOTE_KEY, Note::class.java) as Note }
        } else {
            bundle?.let { it.getSerializable(Constants.NOTE_KEY) as Note }
        }
        return note
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            NotesListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
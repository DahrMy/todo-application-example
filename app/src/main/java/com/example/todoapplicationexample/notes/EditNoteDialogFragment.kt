package com.example.todoapplicationexample.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapplicationexample.databinding.FragmentEditNoteDialogBinding

class EditNoteDialogFragment : Fragment() {

    private var _binding: FragmentEditNoteDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteDialogBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EditNoteDialogFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
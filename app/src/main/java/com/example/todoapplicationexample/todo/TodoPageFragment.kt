package com.example.todoapplicationexample.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapplicationexample.R

class TodoPageFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_todo_page, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TodoPageFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
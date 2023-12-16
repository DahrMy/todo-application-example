package com.example.todoapplicationexample.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapplicationexample.databinding.FragmentProfileSectionBinding

class ProfileSectionFragment : Fragment() {

    private var _binding: FragmentProfileSectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSectionBinding.inflate(inflater, container, false)
        view = binding.root



        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileSectionFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
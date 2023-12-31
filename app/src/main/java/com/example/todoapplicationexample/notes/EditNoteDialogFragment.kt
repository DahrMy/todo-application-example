package com.example.todoapplicationexample.notes

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.todoapplicationexample.R
import com.example.todoapplicationexample.databinding.FragmentEditNoteDialogBinding
import java.io.InputStream


class EditNoteDialogFragment : Fragment() {

    private var _binding: FragmentEditNoteDialogBinding? = null
    private val binding get() = _binding!!
    private var imageList: MutableList<Drawable> = mutableListOf()

    private lateinit var adapter: EditNoteDialogImagesRecyclerViewAdapter

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        galleryUri -> run {
            imageList.add(galleryUri?.let { uriToDrawable(it) }!!)
            updateRecyclerView(imageList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = EditNoteDialogImagesRecyclerViewAdapter(imageList.toMutableList())
        binding.recyclerviewImages.adapter = adapter

/*        todo: question(Can't add back arrow button to toolbar)
          val activity = activity as AppCompatActivity
          activity.setSupportActionBar(binding.toolbar)
          activity.setDisplayHomeAsUpEnabled(true)
          binding.toolbar.setNavigationOnClickListener { getOnBackPressedDispatcher().onBackPressed() }
*/
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.attach_image -> {
                    galleryLauncher.launch("image/*")
                    true
                }
                R.id.make_photo -> false
                R.id.save -> false
                else -> false
            }
        }
    }

    private fun updateRecyclerView(newList: List<Drawable>) {
        adapter.updateList(newList)
    }

    private fun uriToDrawable(uri: Uri): Drawable? {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        return Drawable.createFromStream(inputStream, uri.toString())
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
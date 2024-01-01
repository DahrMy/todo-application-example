package com.example.todoapplicationexample.notes

import android.content.ContentValues
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.todoapplicationexample.R
import com.example.todoapplicationexample.databinding.FragmentEditNoteDialogBinding
import java.io.File
import java.io.InputStream
import java.io.OutputStream


class EditNoteDialogFragment : Fragment() {

    private var _binding: FragmentEditNoteDialogBinding? = null
    private val binding get() = _binding!!
    private var imageList: MutableList<Drawable> = mutableListOf()

    private lateinit var adapter: EditNoteDialogImagesRecyclerViewAdapter

    private lateinit var tempImageUri: Uri
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        galleryUri -> run {
            imageList.add(galleryUri?.let { uriToDrawable(it) }!!)
            updateRecyclerView(imageList)
        }
    }
    private val photoLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        uriToDrawable(tempImageUri)?.let { it1 -> imageList.add(it1) }
        updateRecyclerView(imageList)
/*
        val contentResolver = requireContext().contentResolver

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        val imageUriInGallery: Uri? =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            val outputStream: OutputStream? = imageUriInGallery?.let {
                contentResolver.openOutputStream(it)
            }

            outputStream?.use { outputStream ->
                val inputStream: InputStream? = tempImageUri.let {
                    contentResolver.openInputStream(it)
                }
            }
        }
 */

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = EditNoteDialogImagesRecyclerViewAdapter(imageList.toMutableList())
        binding.recyclerviewImages.adapter = adapter

        tempImageUri = initTempUri()

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
                R.id.make_photo -> {
                    photoLauncher.launch(tempImageUri)
                    true
                }
                R.id.save -> false
                else -> false
            }
        }
    }

    private fun updateRecyclerView(newList: List<Drawable>) {
        adapter.updateList(newList)
    }

    private fun initTempUri(): Uri {

        val context = requireContext()

        val tempImagesDir = File(context.filesDir, getString(R.string.temp_image_dir))
        tempImagesDir.mkdir()
        val tempImage = File(tempImagesDir, getString(R.string.temp_image))

        return FileProvider.getUriForFile(context, getString(R.string.authority), tempImage)

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
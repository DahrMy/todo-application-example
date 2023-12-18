package com.example.todoapplicationexample.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import com.example.todoapplicationexample.R
import com.example.todoapplicationexample.databinding.FragmentProfileSectionBinding

class ProfileSectionFragment : Fragment() {

    private var _binding: FragmentProfileSectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View

    private var profile: Profile? = null // TODO: change to lateinit var

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        galleryUri -> binding.imageviewProfile.setImageURI(galleryUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileSectionBinding.inflate(inflater, container, false)
        view = binding.root

        initProfileFragmentContent()
        setListeners()

        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initProfileFragmentContent() {
        binding.apply {
            if (profile == null) { setEditMode(true) } else {
                imageviewProfile.setImageDrawable(profile?.image)
                textviewUserName.text = profile!!.name
            }
        }


    }

    private fun setListeners() {
        binding.apply {

            editTextUsername.addTextChangedListener(editTextUserNameTextWatcher())

            buttonEdit.setOnClickListener {
                setEditMode(true)
                editTextUsername.setText(profile?.name)
            }

            buttonConfirm.setOnClickListener {
                val defaultImage = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_account_circle)!! // TODO: Remove bug when image not selected in image picker (only when image have been confirmed before)
                profile = Profile(
                    editTextUsername.text.toString(),
                    imageviewProfile.drawable ?: defaultImage
                )
                imageviewProfile.setImageDrawable(profile!!.image)
                textviewUserName.text = profile!!.name
                setEditMode(false)
            }

            cardviewProfileImage.setOnClickListener {
                galleryLauncher.launch("image/*")
            }

        }
    }

    private fun setEditMode(status: Boolean) {
        binding.apply {
            if (status) {
                buttonEdit.visibility = View.GONE
                textviewUserName.visibility = View.GONE

                imageviewProfile.alpha = 0.1F
                cardviewProfileImage.apply {
                    isEnabled = true
                    isCheckable = true
                    isClickable = true
                    isFocusable = true
                }

                buttonConfirm.visibility = View.VISIBLE
                textInputLayoutUsername.visibility = View.VISIBLE
                imageviewIcProfileImageEdit.visibility = View.VISIBLE

            } else {
                buttonEdit.visibility = View.VISIBLE
                textviewUserName.visibility = View.VISIBLE

                imageviewProfile.alpha = 1F
                cardviewProfileImage.apply {
                    isEnabled = false
                    isCheckable = false
                    isClickable = false
                    isFocusable = false
                }

                buttonConfirm.visibility = View.GONE
                textInputLayoutUsername.visibility = View.GONE
                imageviewIcProfileImageEdit.visibility = View.GONE
            }
        }
    }

    private fun editTextUserNameTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            override fun afterTextChanged(s: Editable?) {
                binding.apply {
                    if (s != null) buttonConfirm.isEnabled = s.isNotBlank()
                }
            }

        }
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
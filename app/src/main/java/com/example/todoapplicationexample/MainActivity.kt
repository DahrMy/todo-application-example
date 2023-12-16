@file:Suppress("ObjectPropertyName")

package com.example.todoapplicationexample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapplicationexample.databinding.ActivityMainBinding
import com.example.todoapplicationexample.profile.ProfileSectionFragment
import com.example.todoapplicationexample.todo.TodoSectionFragment

private var _binding: ActivityMainBinding? = null
private val binding get() = _binding!!

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.bottomNavigationView.setOnItemReselectedListener(this::onBottomNavigationItemClicked)

        setContentView(view)
    }

    private fun onBottomNavigationItemClicked(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.section_todo -> loadFragment(TodoSectionFragment())
            R.id.section_notes -> return false // TODO: connect fragment with notes
            R.id.section_profile -> loadFragment(ProfileSectionFragment())
        }
        return true
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainerView.id, fragment)
                .commit()
        }
    }

}
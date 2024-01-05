package com.example.todoapplicationexample

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todoapplicationexample.databinding.ActivityMainBinding
import com.example.todoapplicationexample.notes.NotesListFragment
import com.example.todoapplicationexample.profile.ProfileSectionFragment
import com.example.todoapplicationexample.todo.TodoSectionFragment

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.bottomNavigationView.setOnItemSelectedListener(this::onBottomNavigationItemClicked)

        setContentView(view)
    }

    private fun onBottomNavigationItemClicked(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.section_todo -> loadFragment(TodoSectionFragment())
            R.id.section_notes -> loadFragment(NotesListFragment())
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
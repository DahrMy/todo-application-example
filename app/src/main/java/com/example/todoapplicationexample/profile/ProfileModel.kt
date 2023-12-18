package com.example.todoapplicationexample.profile

import android.content.Context

// Use this class later
class ProfileModel(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "SHARED_PREFERENCES", Context.MODE_PRIVATE)

    companion object {

    }

}
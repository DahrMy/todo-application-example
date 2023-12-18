package com.example.todoapplicationexample.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Use this class later
class ProfileViewModel(private val model: ProfileModel) : ViewModel() {

    private val _sharedData = MutableLiveData<Profile>()
    val sharedData: LiveData<Profile>
        get() = _sharedData



}
package com.example.timer_kotlin.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StopwatchModel : ViewModel() {

    private val _index = MutableLiveData<Int>()

    fun setIndex(index: Int) {
        _index.value = index
    }
}
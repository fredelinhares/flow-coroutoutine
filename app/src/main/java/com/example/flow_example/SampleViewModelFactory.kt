package com.example.flow_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SampleViewModelFactory(private val repository: SampleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SampleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SampleViewModel(repository) as T
        }
        throw IllegalArgumentException("blablabla")
    }
}
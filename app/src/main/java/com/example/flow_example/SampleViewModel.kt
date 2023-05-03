package com.example.flow_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SampleViewModel(private val repository: SampleRepository) : ViewModel() {

    private val _data = MutableStateFlow<ResultWrapper<List<FakeData>>>(ResultWrapper.Loading)
    val data: StateFlow<ResultWrapper<List<FakeData>>> = _data

    private var dataList = mutableListOf<FakeData>()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                repository.getData().collect { data ->
                    dataList.add(data)
                    _data.value = ResultWrapper.Success(dataList.toList())
                }
            } catch (e: Exception) {
                _data.value = ResultWrapper.Error(e)
            }
        }
    }
}

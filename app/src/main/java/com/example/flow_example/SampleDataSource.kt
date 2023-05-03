package com.example.flow_example

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SampleDataSource {

    fun fetchData(): Flow<FakeData> = flow {
        var counter = 1
        while (true) {
            emit(FakeData(id = counter, name = "Nome $counter"))
            delay(1000)
            counter++
        }
    }
}

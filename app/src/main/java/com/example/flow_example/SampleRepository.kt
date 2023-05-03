package com.example.flow_example

import kotlinx.coroutines.flow.Flow

class SampleRepository(private val dataSource: SampleDataSource) {
    fun getData(): Flow<FakeData> = dataSource.fetchData()
}

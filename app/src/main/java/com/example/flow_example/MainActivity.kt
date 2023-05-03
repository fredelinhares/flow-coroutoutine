package com.example.flow_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.flow_example.ui.theme.FlowexampleTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowexampleTheme {
                val dataSource = SampleDataSource()
                val repository = SampleRepository(dataSource)
                val viewModel: SampleViewModel = viewModel(factory = SampleViewModelFactory(repository))

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: SampleViewModel) {
    val dataState = viewModel.data.collectAsState()

    when (val result = dataState.value) {
        is ResultWrapper.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is ResultWrapper.Success -> {
            val dataList = result.data
            LazyColumn {
                items(dataList) { data ->
                    Text(
                        text = "ID: ${data.id}, Nome: ${data.name}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        is ResultWrapper.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Erro: ${result.exception.message}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

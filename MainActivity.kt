package com.xcvi.manualpagination

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xcvi.manualpagination.ui.theme.ManualPaginationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManualPaginationTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state = viewModel.state
                val listOfItems = state.items

                LazyColumn {
                    items(listOfItems.size) { index ->
                        val item = listOfItems[index]

                        if (index >= listOfItems.size - 1 && !state.endReached && !state.isLoading){
                            viewModel.loadNextItems()
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(9.dp)
                        ) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.headlineLarge,
                                modifier = Modifier.padding(12.dp)
                            )
                            Text(text = item.description, modifier = Modifier.padding(12.dp))
                        }
                    }

                    item {
                        if (state.isLoading){
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ManualPaginationTheme {
        Greeting("Android")
    }
}
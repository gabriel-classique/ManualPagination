package com.xcvi.manualpagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: Repository): ViewModel() {

    var state by mutableStateOf(UiState())

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getItems(nextPage, 20)
        },
        getNextKey = { //the previous list of items is available to generate the next key
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { newList, newKey ->
            state = state.copy(
                items = state.items + newList,
                page = newKey,
                endReached = newList.isEmpty()
            )
        }
    )

    init {
        loadNextItems()
    }

    fun loadNextItems(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }


}


data class UiState(
    val isLoading: Boolean = false,
    val items: List<ItemModel> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
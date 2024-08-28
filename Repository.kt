package com.xcvi.manualpagination

import kotlinx.coroutines.delay

class Repository {

    private val dataSource = (1..100).map{
        ItemModel(
            title = "Item #$it",
            description = "This is the description of Item #$it"
        )
    }

    suspend fun getItems(page: Int, pageSize: Int): Result<List<ItemModel>>{  //page can be other data type
        delay(2000)

        val startingIndex = page * pageSize

        val result = if(startingIndex + pageSize <= dataSource.size){
            Result.success(dataSource.slice(startingIndex until startingIndex + pageSize))
        } else{
            Result.success(emptyList())
        }

        return result
    }
}


data class ItemModel(
    val title: String,
    val description: String
)
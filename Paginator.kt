package com.xcvi.manualpagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}

/*
* Key can be any data type.. in this case its an integer
 */
package com.example.notebook.data

import com.example.notebook.data.model.Item

/**
 * Main entry point for accessing item data.
 *
 *
 * should add callbacks to other methods to inform the user of network/database errors or successful operations.
 * usually every operation on database or network should be executed in a different thread.
 */
interface ItemDataSource {

    interface LoadItemsCallback {
        fun onItemsLoaded(items: List<Item>)
        fun onDataNotAvailable()
    }

    interface GetItemCallback {

        fun onItemLoaded(item: Item)

        fun onDataNotAvailable()
    }

    fun getItems(callback: LoadItemsCallback)
    fun getItemById(id: Int, callback: GetItemCallback)
    fun saveItem(item: Item)
    fun updateItem(item: Item)
    fun getItemsByTag(tag: String, callback: LoadItemsCallback)
}
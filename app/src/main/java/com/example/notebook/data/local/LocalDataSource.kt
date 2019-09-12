package com.example.notebook.data.local

import android.util.Log
import com.example.notebook.AppExecutors
import com.example.notebook.data.model.Item
import com.example.notebook.data.ItemDataSource

class LocalDataSource private constructor(val appExecutors: AppExecutors, val itemDAO: ItemDao) : ItemDataSource {

    private val TAG = javaClass.simpleName

    /**
     * Callback is fired if the database doesn't exist
     * or the table is empty.
     */
    override fun getItems(callback: ItemDataSource.LoadItemsCallback) {

        // Run on another thread while fetching
        appExecutors.diskIO.execute {

            // Fetch items from the local database
            val items = itemDAO.getItems()

            // Run on the main thread
            appExecutors.mainThread.execute {

                // We did not get any items
                if (items.isEmpty()) {

                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable()

                } else {

                    // This will be called if we are able to fetch items from the database.
                    callback.onItemsLoaded(items)
                }
            }
        }

    }

    /**
     * Get a item by id
     */
    override fun getItemById(id: Int, callback: ItemDataSource.GetItemCallback) {

        // Run on another thread while fetching
        appExecutors.diskIO.execute {
            val item = itemDAO.getItemById(id)

            // Run on the main thread
            appExecutors.mainThread.execute {
                if (item != null) {
                    callback.onItemLoaded(item)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    /**
     * Get a items by tag
     */
    override fun getItemsByTag(tag: String, callback: ItemDataSource.LoadItemsCallback) {

        // Run on another thread while fetching
        appExecutors.diskIO.execute {

            // Fetch items from the local database
            val items = itemDAO.getItemsByTag(tag)

            // Run on the main thread
            appExecutors.mainThread.execute {

                // We did not get any items
                if (items.isEmpty()) {

                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable()

                } else {

                    // This will be called if we are able to fetch items from the database.
                    callback.onItemsLoaded(items)
                }
            }
        }
    }

    override fun saveItem(item: Item) {
        appExecutors.diskIO.execute{itemDAO.insertItem(item)}
    }

    override fun updateItem(item: Item) {
        appExecutors.diskIO.execute {itemDAO.updateItem(item)}
    }

    /**
     * Return an instance of this class
     */
    companion object {
        private var INSTANCE: LocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, itemDao: ItemDao): LocalDataSource {
            if (INSTANCE == null) {
                synchronized(LocalDataSource::javaClass) {
                    INSTANCE = LocalDataSource(appExecutors, itemDao)
                }
            }
            return INSTANCE!!
        }
    }
}
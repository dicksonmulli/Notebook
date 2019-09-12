package com.example.notebook.data

import com.example.notebook.data.local.LocalDataSource
import com.example.notebook.data.model.Item
import java.util.*

/**
 * Implementation to load items from the data sources into a cache.
 *
 *
 * Implements synchronisation between locally persisted data and data
 * obtained from the server(if there is)
 */
class ItemRepository(val itemDataSource: LocalDataSource) : ItemDataSource {

    private val TAG = javaClass.simpleName

    var mCachedItems: LinkedHashMap<Int, Item> = LinkedHashMap()

    /**
     * Gets items from cache or local data source (SQLite) whichever is
     * available first.
     *
     *
     * Item: [LoadTasksCallback.onDataNotAvailable] is fired if all data sources fail to
     * get the data.
     */
    override fun getItems(callback: ItemDataSource.LoadItemsCallback) {

        // Respond immediately with cache if available
//        if (mCachedItems.isNotEmpty()) {
//            //callback.onItemsLoaded(LiveData<ArrayList(mCachedItems.values)>)
//            return
//        }

        // If the cache is empty we need to fetch data from db
        itemDataSource.getItems(object : ItemDataSource.LoadItemsCallback {
            override fun onItemsLoaded(items: List<Item>) {
                // This will repopulate the cache
                refreshCache(items)

                // Response with data
                callback.onItemsLoaded(items)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    private fun refreshCache(item: List<Item>) {

        mCachedItems.clear()

        item.forEach {
            cacheAndPerform(it)
        }
    }

    private fun cacheAndPerform(item: Item) {

        mCachedItems.put(item.id, item)
    }

    /**
     * This method gets a certain item by id
     */
    override fun getItemById(id: Int, callback: ItemDataSource.GetItemCallback) {
        itemDataSource.getItemById(id, object : ItemDataSource.GetItemCallback {
            override fun onItemLoaded(item: Item) {
                callback.onItemLoaded(item)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    /**
     * This method saves a certain restaurant
     */
    override fun saveItem(item: Item) {
        itemDataSource.saveItem(item)
    }

    /**
     * This method gets all the items by a certain tag
     */
    override fun getItemsByTag(tag: String, callback: ItemDataSource.LoadItemsCallback) {
        itemDataSource.getItemsByTag(tag, object : ItemDataSource.LoadItemsCallback {
            override fun onItemsLoaded(items: List<Item>) {
                callback.onItemsLoaded(items)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }

        })
    }

    /**
     * This method to update a item
     */
    override fun updateItem(item: Item) {
        itemDataSource.updateItem(item)
    }


    companion object {

        private var INSTANCE: ItemRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(itemLocalDataSource: LocalDataSource) =
                INSTANCE ?: synchronized(ItemRepository::class.java) {
                            INSTANCE ?: ItemRepository(itemLocalDataSource)
                                    .also { INSTANCE = it }
                        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }

    }
}
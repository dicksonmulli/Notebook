package com.example.notebook.data.local

import android.arch.persistence.room.*
import com.example.notebook.data.model.Item

@Dao
interface ItemDao {

    /**
     * Select all items from the items table.
     *
     * @return all items.
     */
    @Query("SELECT * FROM items")
    fun getItems(): List<Item>

    /**
     * Select items by tag from the items table.
     *
     * @return list of items.
     */
    @Query("SELECT * FROM items WHERE tag = :tag")
    fun getItemsByTag(tag: String): List<Item>

    /**
     * Select item by id from the items table.
     *
     * @return the item.
     */
    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Item

    /**
     * Update an item.
     *
     * @param item item to be updated
     */
    @Update
    fun updateItem(item: Item): Int

    /**
     * Insert a item in the database. If the item already exists, create another one.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: Item)

}


package com.example.notebook.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.notebook.data.model.Item

/**
 * The Room Database that contains the item table.
 */
@Database(entities = arrayOf(Item::class), version = 2, exportSchema = false)
abstract class ItemDb : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private var INSTANCE: ItemDb? = null

        private val lock = Any()

        @JvmStatic
        fun getInstance(context: Context): ItemDb {

            // When calling this instance concurrently from multiple threads we're in serious trouble:
            // So we use this method, 'synchronized' to lock the instance
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ItemDb::class.java, "items.db")
                            .fallbackToDestructiveMigration()  // temporary
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}
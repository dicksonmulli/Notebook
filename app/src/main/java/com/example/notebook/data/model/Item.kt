package com.example.notebook.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull
import java.io.Serializable


/**
 * Immutable model class for a Item. In order to compile with Room, we can't use @JvmOverloads to generate multiple constructors.
 * id of the task
 */

// as it denotes, is using the table name called “items”. If name is not specified, by default class name is used as the Table name.
@Entity(tableName = "items")
data class Item constructor(

        @NonNull @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
        var id: Int = 0,
        @ColumnInfo(name = "title")
        var title: String = "",
        @ColumnInfo(name = "description")
        var description: String = "",
        @ColumnInfo(name = "tag")
        var tag: String = "") : Serializable
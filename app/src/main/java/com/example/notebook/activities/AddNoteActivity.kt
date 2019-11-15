package com.example.notebook.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.notebook.AppExecutors
import com.example.notebook.R
import com.example.notebook.data.model.Item
import com.example.notebook.data.local.LocalDataSource
import com.example.notebook.data.local.ItemDb
import com.example.notebook.data.ItemRepository
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    private var mRepository: ItemRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar?.let { setSupportActionBar(it) }

        mRepository = ItemRepository.getInstance(LocalDataSource.getInstance(AppExecutors(), ItemDb.getInstance(this).itemDao()))

        saveButton.setOnClickListener {
            mRepository?.saveItem(Item(title = noteTitle.text.toString(), description = description.text.toString(), tag = tag.text.toString()))

            finish()
        }
    }
}

package com.example.notebook.activities

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.notebook.R
import com.example.notebook.data.model.Item


class DetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val b = intent.extras
        var itemObject: Item? = null
        if (b != null)
            itemObject = b.getSerializable("item") as Item

        val tile = findViewById<TextView>(R.id.title)
        val description = findViewById<TextView>(R.id.description)
        val tag = findViewById<TextView>(R.id.tag)

        if (itemObject != null) {
            tile.text = itemObject.title
            description.text = itemObject.description
            tag.text = itemObject.tag
        }
    }
}

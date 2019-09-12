package com.example.notebook.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.notebook.data.model.Item

class MainActivityViewModel : ViewModel() {

    private val _itemLiveData = MutableLiveData<List<Item>>()

    /**
     * will be accessed
     */
    val itemLiveData: LiveData<List<Item>>
        get() = _itemLiveData
}
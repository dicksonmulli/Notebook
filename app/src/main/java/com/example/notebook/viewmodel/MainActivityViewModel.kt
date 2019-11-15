package com.example.notebook.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notebook.data.model.Item

class MainActivityViewModel : ViewModel() {

    private val _itemLiveData = MutableLiveData<List<Item>>()

    /**
     * will be accessed
     */
    val itemLiveData: LiveData<List<Item>>
        get() = _itemLiveData

    /**
     * Implementation of scope
     *
     * ______________________________
     * When to use different Scopes
     * ______________________________
     *
     * 1. WorkManager Scope -Use it when the the task is;-
     *      -> deferrable (task which is still useful even if it's not run immediately)
     *      -> guaranteed to run
     *      e.g sending a twit message
     *
     * 2. Application scope - Use it if the task you want to run doesn't have to complete
     *      e.g cleaning up the database
     */
    init {
        //viewModelScope.launch {}
    }
}
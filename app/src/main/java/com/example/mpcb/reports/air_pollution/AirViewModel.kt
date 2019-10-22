package com.example.mpcb.reports.air_pollution

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel


class AirViewModel : BaseViewModel<AirNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<String>>()

    fun getSourceList() = sourceList

    fun populateData() {
        val list = arrayListOf("Item 1", "Item 2")
        sourceList.value = list
    }

    fun deleteItem() {
        val list = sourceList.value
        if (list!!.size > 1) {
            list.removeAt(list.size - 1)
            sourceList.value = list
        }
    }

    fun addItem() {
        val list = sourceList.value
        list!!.add("Item")
        sourceList.value = list
    }
}
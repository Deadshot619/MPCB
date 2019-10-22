package com.example.mpcb.reports.oms_ambient_air

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel


class OMSAmbientAirViewModel : BaseViewModel<OMSAmbientAirNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<ArrayList<String>>>()

    fun getSourceList() = sourceList

    fun populateData() {
        val parentList = ArrayList<ArrayList<String>>()
        val childList = arrayListOf("Child Item 1")
        parentList.add(childList)
        sourceList.value = parentList
    }

    fun deleteItem() {
        val list = sourceList.value
        if (list!!.size > 1) {
            list.removeAt(list.size - 1)
            sourceList.value = list
        }
    }

    fun addItem() {
        val parentList = sourceList.value
        val childList = arrayListOf("Child Item")
        parentList!!.add(childList)
        sourceList.value = parentList
    }
}
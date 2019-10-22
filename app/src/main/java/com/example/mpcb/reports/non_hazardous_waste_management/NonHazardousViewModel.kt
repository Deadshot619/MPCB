package com.example.mpcb.reports.non_hazardous_waste_management

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.profile.ProfileNavigator


class NonHazardousViewModel : BaseViewModel<ProfileNavigator>() {

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
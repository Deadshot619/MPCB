package com.example.mpcb.reports.hazardous_waste_management

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.RoutineReportHazardousWaste


class HazardousViewModel : BaseViewModel<HazardousNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportHazardousWaste>>()


    fun getSourceList() = sourceList

    fun populateData(list: ArrayList<RoutineReportHazardousWaste>? = arrayListOf(RoutineReportHazardousWaste())) {
        if (list?.size!! >= 1){
            sourceList.value = list
        }else{
            sourceList.value = arrayListOf(RoutineReportHazardousWaste())
        }
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
        list!!.add(RoutineReportHazardousWaste())
        sourceList.value = list
    }
}
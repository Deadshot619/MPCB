package com.example.mpcb.reports.non_hazardous_waste_management

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.RoutineReportNonHazardousWaste


class NonHazardousViewModel : BaseViewModel<NonHazardousNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportNonHazardousWaste>>()

    fun getSourceList() = sourceList

    fun populateData(list: ArrayList<RoutineReportNonHazardousWaste>? = arrayListOf(RoutineReportNonHazardousWaste())) {
        if (list?.size!! >= 1){
            sourceList.value = list
        }else{
            sourceList.value = arrayListOf(RoutineReportNonHazardousWaste())
        }
    }

    fun deleteItem() {
        val list = sourceList.value
        if (list != null && list.size > 1) {
            list.removeAt(list.size - 1)
            sourceList.value = list
        }
    }

    fun addItem() {
        val list = sourceList.value
        list!!.add(RoutineReportNonHazardousWaste())
        sourceList.value = list
    }
}
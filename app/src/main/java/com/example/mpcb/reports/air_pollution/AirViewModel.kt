package com.example.mpcb.reports.air_pollution

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.RoutineReportAirPollution


class AirViewModel : BaseViewModel<AirNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportAirPollution>>()

    fun getSourceList() = sourceList

    fun populateData() {
        val list = arrayListOf(RoutineReportAirPollution())
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
        list!!.add(RoutineReportAirPollution())
        sourceList.value = list
    }
}
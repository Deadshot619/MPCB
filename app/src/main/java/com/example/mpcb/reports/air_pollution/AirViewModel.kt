package com.example.mpcb.reports.air_pollution

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.RoutineReportAirPollution


class AirViewModel : BaseViewModel<AirNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportAirPollution>>()

    fun getSourceList() = sourceList

    fun populateData(list: ArrayList<RoutineReportAirPollution>? = arrayListOf(RoutineReportAirPollution())) {
//        val list = arrayListOf(RoutineReportAirPollution())
        if (list?.size!! < 1)
            sourceList.value = arrayListOf(RoutineReportAirPollution())
        else
            sourceList.value = list
    }

    fun deleteItem() {
        val list = sourceList.value
        list?.size?.run {
            if (list.size > 1) {
                list.removeAt(list.size - 1)
                sourceList.value = list
            }
        }
    }

    fun addItem() {
        val list = sourceList.value
        list?.run {
            list.add(RoutineReportAirPollution())
            sourceList.value = list
        }
    }
}
package com.example.mpcb.reports.bank_guarantee_details

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.RoutineReportBankDetail


class BGDViewModel : BaseViewModel<BGDNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportBankDetail>>()

    fun getSourceList() = sourceList

    fun populateData() {
        val list = arrayListOf(RoutineReportBankDetail())
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
        list!!.add(RoutineReportBankDetail())
        sourceList.value = list
    }
}
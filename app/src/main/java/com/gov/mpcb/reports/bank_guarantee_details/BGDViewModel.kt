package com.gov.mpcb.reports.bank_guarantee_details

import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.request.RoutineReportBankDetail


class BGDViewModel : BaseViewModel<BGDNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportBankDetail>>()

    fun getSourceList() = sourceList

    fun populateData(list: ArrayList<RoutineReportBankDetail>? = arrayListOf(RoutineReportBankDetail())) {
        if (list?.size!! >= 1){
            sourceList.value = list
        }else{
            sourceList.value = arrayListOf(RoutineReportBankDetail())
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
        list!!.add(RoutineReportBankDetail())
        sourceList.value = list
    }
}
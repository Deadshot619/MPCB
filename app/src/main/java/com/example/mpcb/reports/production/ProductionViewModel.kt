package com.example.mpcb.reports.production

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.RoutineReportProduct


class ProductionViewModel : BaseViewModel<ProductionNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportProduct>>()

    fun getSourceList() = sourceList

    fun populateData() {
        val list = arrayListOf(RoutineReportProduct())
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
        list!!.add(RoutineReportProduct())
        sourceList.value = list
    }
}
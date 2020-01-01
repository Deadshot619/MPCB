package com.gov.mpcb.reports.production

import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.request.RoutineReportProduct


class ProductionViewModel : BaseViewModel<ProductionNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<RoutineReportProduct>>()

    fun getProductList() = sourceList

    fun populateData(list: ArrayList<RoutineReportProduct>? = arrayListOf(RoutineReportProduct())) {
        if (list?.size!! >= 1 )
            sourceList.value = list
        else
            sourceList.value = arrayListOf(RoutineReportProduct())
    }

    fun deleteItem() {
        val list = sourceList.value
        if (list?.size!! > 1) {
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
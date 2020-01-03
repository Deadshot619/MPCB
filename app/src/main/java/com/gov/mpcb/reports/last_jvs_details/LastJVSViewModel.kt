package com.gov.mpcb.reports.last_jvs_details

import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.request.JvsSampleCollectedWaterSource
import com.gov.mpcb.network.request.LastJVSChild


class LastJVSViewModel : BaseViewModel<LastJVSNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<JvsSampleCollectedWaterSource>>()

    fun getSourceList() = sourceList

    fun populateData(list: ArrayList<JvsSampleCollectedWaterSource>? = arrayListOf(
        JvsSampleCollectedWaterSource()
    )) {
        if (list?.size!! >= 1){
            sourceList.value = list
        }else{
            val parentModel = JvsSampleCollectedWaterSource()
            val childList = arrayListOf(LastJVSChild())
            parentModel.lastJvsChild = childList
            sourceList.value = arrayListOf(parentModel)
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
        val parentList = sourceList.value!!
        val parentModel = JvsSampleCollectedWaterSource()
        val childList = arrayListOf(LastJVSChild())
        parentModel.lastJvsChild = childList
        parentList.add(parentModel)
        sourceList.value = parentList
    }

    fun getReportData(): ArrayList<JvsSampleCollectedWaterSource> {
        val data = sourceList.value!!
        val parameterList = arrayListOf<String>()
        val prescribedValueList = arrayListOf<String>()

        for (parentItem in data) {
            parentItem.jvsWaterSourceParameter.clear()
            parentItem.jvsWaterSourceStdPrescribed.clear()
            for (childItem in parentItem.lastJvsChild) {
                parameterList.add(childItem.parameter)
                prescribedValueList.add(childItem.prescribedValue)
            }
            parentItem.jvsWaterSourceParameter.addAll(parameterList)
            parentItem.jvsWaterSourceStdPrescribed.addAll(prescribedValueList)
            parameterList.clear()
            prescribedValueList.clear()
        }
        return data
    }
}
package com.gov.mpcb.reports.oms_ambient_air

import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.request.AmbientAirChild
import com.gov.mpcb.network.request.JvsSampleCollectedAirSource


class OMSAmbientAirViewModel : BaseViewModel<OMSAmbientAirNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<JvsSampleCollectedAirSource>>()

    fun getSourceList() = sourceList

    fun populateData(list: ArrayList<JvsSampleCollectedAirSource>? = arrayListOf(JvsSampleCollectedAirSource())) {
//TODO 22/11/19 Try Changing the value in if condition
        if (list?.size!! >= 1){
            sourceList.value = list
        }else{
            val parentModel = JvsSampleCollectedAirSource()
            val childList = arrayListOf(AmbientAirChild())
            parentModel.ambientAirChild = childList
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
        val parentList = sourceList.value
        val parentModel = JvsSampleCollectedAirSource()
        val childList = arrayListOf(AmbientAirChild())
        parentModel.ambientAirChild = childList
        parentList!!.add(parentModel)
        sourceList.value = parentList
    }

    fun getReportData(): ArrayList<JvsSampleCollectedAirSource> {
        val data = sourceList.value!!
        val parameterList = arrayListOf<String>()
        val prescribedValueList = arrayListOf<String>()
        for (parentItem in data) {
            parentItem.jvsAirSourceParameter.clear()
            parentItem.jvsAirSourceStdPrescribed.clear()
            for (childItem in parentItem.ambientAirChild) {
                parameterList.add(childItem.parameter)
                prescribedValueList.add(childItem.prescribedValue)
            }
            parentItem.jvsAirSourceParameter.addAll(parameterList)
            parentItem.jvsAirSourceStdPrescribed.addAll(prescribedValueList)
            parameterList.clear()
            prescribedValueList.clear()
        }
        return data
    }
}
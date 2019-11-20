package com.example.mpcb.reports.oms_ambient_air

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.request.AmbientAirChild
import com.example.mpcb.network.request.JvsSampleCollectedAirSource


class OMSAmbientAirViewModel : BaseViewModel<OMSAmbientAirNavigator>() {

    private val sourceList = MutableLiveData<ArrayList<JvsSampleCollectedAirSource>>()

    fun getSourceList() = sourceList

    fun populateData() {
        val parentModel = JvsSampleCollectedAirSource()
        val childList = arrayListOf(AmbientAirChild())
        parentModel.ambientAirChild = childList
        sourceList.value = arrayListOf(parentModel)
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
        val parentModel = JvsSampleCollectedAirSource()
        val childList = arrayListOf(AmbientAirChild())
        parentModel.ambientAirChild = childList
        parentList.add(parentModel)
        sourceList.value = parentList
    }

    fun getReportData(): ArrayList<JvsSampleCollectedAirSource> {
        val data = sourceList.value!!
        val parameterList = arrayListOf<String>()
        val prescribedValueList = arrayListOf<String>()
        for (parentItem in data) {
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
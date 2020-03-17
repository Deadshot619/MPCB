package com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.menu_tabs.surprise_inspections.SurpriseInspectionActivity
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.AddSurpriseInspectionRequest
import com.gov.mpcb.network.request.ViewPreviousInspectionListRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.ViewPreviousInspectionListData
import com.gov.mpcb.utils.LoadingStatus
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer

class ApplyForSurpriseInspectionViewModel : BaseViewModel<ApplyForSurpriseInspectionNavigator>() {

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

    //Variable to hold [ViewPreviousInspectionListData] data
    private val previousInspectionListData = MutableLiveData<List<ViewPreviousInspectionListData>>()
    val _previousInspectionListData: LiveData<List<ViewPreviousInspectionListData>>
        get() = previousInspectionListData

    /**
     * This method is used to submit the Surprise Inspection application form
     *
     * @param industryInn Takes Industry Inspection number as input
     * @param date take a date as String
     * @param reason takes a reason to visit an industry for inspection as String
     */
    fun submitInspectionForm(industryInn: String, date: String, reason: String){
        val request = AddSurpriseInspectionRequest().apply {
            userId = user.userId.toString()
            this.industryIin = industryInn
            surpriseInspectionOn = date
            reasonForConductingSurpriseInspection = reason
        }

        dialogVisibility.value = true
        dialogMessage.value = "Submitting Application. Please wait..."

        mDisposable.add(
            DataProvider.addSurpriseInspections(
                request = request,
                success = Consumer {
                    dialogVisibility.value = false
                    mNavigator?.showToast(it.message)
                    if (it.status == 1)
                        mNavigator?.openActivity(SurpriseInspectionActivity())
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }

    /**
     * This method is used to load Previously conducted Inspections Data
     *
     * @param industryIin Takes Industry Inspection Number as input
     */
    fun loadPreviouslyConductedInspections(industryIin: String){
        progressStatus.value = LoadingStatus.LOADING

        val request = ViewPreviousInspectionListRequest().apply {
            this.industryIin = industryIin
        }

        mDisposable.add(
            DataProvider.getPreviousConductedInspections(
                request = request,
                success = Consumer {
                    previousInspectionListData.value = it.data
                    progressStatus.value = LoadingStatus.DONE
                },
                error = Consumer {
                    checkError(it)
                    progressStatus.value = LoadingStatus.ERROR
                }
            )
        )
    }

}
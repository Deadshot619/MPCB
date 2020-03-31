package com.gov.mpcb.menu_tabs.industry_directory.consent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewIndustryDirectoryDataRequest
import com.gov.mpcb.network.response.IdConsentData
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.LoadingStatus
import com.gov.mpcb.utils.fromJson
import io.reactivex.functions.Consumer

class IdConsentViewModel: BaseViewModel<IdConsentNavigator>() {


    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

    //Variable to hold [ViewAppliedListResponse] data
    private val idConsentData = MutableLiveData<List<IdConsentData>>()
    val _idConsentData : LiveData<List<IdConsentData>>
        get() = idConsentData

    //Get data for application list
    fun getIndustryData(industryId: Int){
        val request = ViewIndustryDirectoryDataRequest().apply {
            this.industryId = industryId
            industryDirectoryType = IndustryDirectoryType.Consent
        }

        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getApplicationListData(
                request = request,
                success = Consumer {
                    idConsentData.value = Gson().fromJson<List<IdConsentData>>(it.data.toString())
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
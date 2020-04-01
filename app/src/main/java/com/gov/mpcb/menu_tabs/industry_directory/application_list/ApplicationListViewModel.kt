package com.gov.mpcb.menu_tabs.industry_directory.application_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewIndustryDirectoryDataRequest
import com.gov.mpcb.network.response.IdIndustryData
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.LoadingStatus
import io.reactivex.functions.Consumer

class ApplicationListViewModel: BaseViewModel<ApplicationListNavigator>() {

    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

    //Variable to hold [ViewAppliedListResponse] data
    private val viewIndustryData = MutableLiveData<IdIndustryData>()
    val _viewIndustryData : LiveData<IdIndustryData>
        get() = viewIndustryData

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
                    viewIndustryData.value = it.industryData
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

package com.gov.mpcb.menu_tabs.industry_directory.visits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewIndustryDirectoryDataRequest
import com.gov.mpcb.network.response.IdVisitData
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.LoadingStatus
import com.gov.mpcb.utils.fromJson
import io.reactivex.functions.Consumer

class IdVisitsViewModel : BaseViewModel<IdVisitsNavigator>() {

    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

    //Variable to hold [ViewAppliedListResponse] data
    private val data = MutableLiveData<List<IdVisitData>>()
    val _data: LiveData<List<IdVisitData>>
        get() = data

    //Get data for application list
    fun getIndustryData(industryId: Int, industryDirectoryType: IndustryDirectoryType){
        val request = ViewIndustryDirectoryDataRequest().apply {
            this.industryId = industryId
            this.industryDirectoryType = industryDirectoryType
        }

        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getApplicationListData(
                request = request,
                success = Consumer {
                    data.value = Gson().fromJson<List<IdVisitData>>(it.data.toString())
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

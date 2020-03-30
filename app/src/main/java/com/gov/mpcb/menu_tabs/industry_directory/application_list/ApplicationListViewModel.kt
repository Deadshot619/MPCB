package com.gov.mpcb.menu_tabs.industry_directory.application_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewIndustryDirectoryDataRequest
import com.gov.mpcb.network.response.IdIndustryData
import com.gov.mpcb.utils.IndustryDirectoryType
import io.reactivex.functions.Consumer

class ApplicationListViewModel: BaseViewModel<ApplicationListNavigator>() {

    //Variable to hold [ViewAppliedListResponse] data
    private val viewIndustryData = MutableLiveData<IdIndustryData>()
    val _viewIndustryData : LiveData<IdIndustryData>
        get() = viewIndustryData

    fun getIndustryData(industryId: Int){
        val request = ViewIndustryDirectoryDataRequest().apply {
            this.industryId = industryId
            type = IndustryDirectoryType.Consent.value
        }

        mDisposable.add(
            DataProvider.getApplicationListData(
                request = request,
                success = Consumer {
                    viewIndustryData.value = it.industryData
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }

}


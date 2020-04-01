package com.gov.mpcb.menu_tabs.industry_directory.application_list

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.menu_tabs.industry_directory.authorization.IdAuthorizationFragment
import com.gov.mpcb.menu_tabs.industry_directory.consent.IdConsentFragment
import com.gov.mpcb.menu_tabs.industry_directory.submission.IdSubmissionFragment
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewIndustryDirectoryDataRequest
import com.gov.mpcb.network.response.IdIndustryData
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.LoadingStatus
import io.reactivex.functions.Consumer

class ApplicationListViewModel: BaseViewModel<ApplicationListNavigator>() {

    /**
     * This variable holds the data for Pager in a map of key value pairs (Name corresponding to its Fragment)
     */
    private val PAGER_DATA = mapOf<Int, Fragment>(
        R.string.consent to IdConsentFragment(),
        R.string.authorization to IdAuthorizationFragment(),
        R.string.submission to IdSubmissionFragment()
    )
    val PAGER_KEYS: List<Int>
        get() = PAGER_DATA.keys.toList()
    val PAGER_VALUES: List<Fragment>
        get() = PAGER_DATA.values.toList()

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


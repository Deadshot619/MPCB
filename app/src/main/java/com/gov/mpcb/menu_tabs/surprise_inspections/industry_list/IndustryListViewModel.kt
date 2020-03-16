package com.gov.mpcb.menu_tabs.surprise_inspections.industry_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewAvailableIndustriesRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.ViewAvailableIndustriesData
import com.gov.mpcb.utils.LoadingStatus
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer

class IndustryListViewModel : BaseViewModel<IndustryListNavigator>(){

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

    //Variable to hold [ViewAppliedListResponse] data
    private val viewAvailableIndustriesData = MutableLiveData<List<ViewAvailableIndustriesData>>()
    val _viewAvailableIndustriesData : LiveData<List<ViewAvailableIndustriesData>>
        get() = viewAvailableIndustriesData

/*    val totalPage = MutableLiveData<Int>(0)
    val currentPage = MutableLiveData<Int>(0)
    */
    init {
        //Call this method only if network is available
        if (isNetworkAvailable())
            getAvailableIndustryListsData()
    }


    /**
     * This method calls the view_applied_list Api & sets the data to [viewAvailableIndustriesData]
     */
    fun getAvailableIndustryListsData(searchQuery: String = "") {
        val request = ViewAvailableIndustriesRequest().apply {
            userId = user.userId.toString()
            searchString = searchQuery
        }

        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getAvailableIndustries(
                request = request,
                success = Consumer {
                    viewAvailableIndustriesData.value = it.data
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
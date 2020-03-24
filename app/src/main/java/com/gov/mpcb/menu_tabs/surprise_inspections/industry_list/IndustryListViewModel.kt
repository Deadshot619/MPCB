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
import kotlin.math.ceil

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

    //This variable holds the data(total no. of pages)for pagination
    private val totalPage = MutableLiveData<Int>(0)
    val _totalPage : LiveData<Int>
        get() = totalPage

    //This variable holds the data(current page)for pagination
    private val currentPage = MutableLiveData<Int>(1)
    val _currentPage: LiveData<Int>
        get() = currentPage

    init {
        //Call this method only if network is available
        if (isNetworkAvailable())
            getAvailableIndustryListsData()
    }


    /**
     * This method calls the view_applied_list Api & sets the data to [viewAvailableIndustriesData]
     */
    fun getAvailableIndustryListsData(searchQuery: String = "", pageNo: Int = 1) {
        val request = ViewAvailableIndustriesRequest().apply {
            userId = user.userId.toString()
            searchString = searchQuery
            page = pageNo
        }

        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getAvailableIndustries(
                request = request,
                success = Consumer {
                    viewAvailableIndustriesData.value = it.data
                    //Divide the total rows by 25 so that we get total no. of pages
                    totalPage.value = ceil(it.total_rows / 25.00).toInt()
                    progressStatus.value = LoadingStatus.DONE
                },
                error = Consumer {
                    checkError(it)
                    progressStatus.value = LoadingStatus.ERROR
                }
            )
        )
    }

    fun incrementCurrentPage(){
        currentPage.value = currentPage.value!! + 1
    }

    fun decrementCurrentPage(){
        currentPage.value = currentPage.value!! - 1
    }

    fun resetCurrentPage(){
        currentPage.value = 1
    }


}
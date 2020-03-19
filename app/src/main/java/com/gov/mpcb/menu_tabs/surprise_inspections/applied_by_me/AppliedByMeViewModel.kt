package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewAppliedListRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.utils.LoadingStatus
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer
import kotlin.math.ceil

class AppliedByMeViewModel : BaseViewModel<AppliedByMeNavigator>() {


    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    //Variable to hold [ViewAppliedListResponse] data
    private val viewAppliedLists = MutableLiveData<List<ViewAppliedListData>>()
    val _viewAppliedLists: LiveData<List<ViewAppliedListData>>
        get() = viewAppliedLists

    //This variable holds the data(total no. of pages)for pagination
    val totalPage = MutableLiveData<Int>(0)
    val currentPage = MutableLiveData<Int>(1)


    init {
        //Call this method only if network is available
        if (isNetworkAvailable())
            getAppliedListsData()
    }

    /**
     * This method filters data for Verified Surprise Inspection section.
     * Pass value as true if the List passed is for [AppliedByMeFragment], otherwise false
     *
     * @param list List of [ViewAppliedListData]
     * @param dataForAppliedByMe Takes a Boolean as input
     */
    fun filterData(
        list: List<ViewAppliedListData>,
        dataForAppliedByMe: Boolean
    ): List<ViewAppliedListData> {
        return if (dataForAppliedByMe)
            list.filter { it.is_approved_by_hod != 1 }
        else
            list.filter { it.is_approved_by_hod == 1 }
    }

    /**
     * This method calls the view_applied_list Api & sets the data to [viewAppliedLists]
     */
    fun getAppliedListsData(pageNo: Int = 1) {
        val request = ViewAppliedListRequest().apply {
            userId = user.userId.toString()
            page = pageNo
        }

        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getAppliedLists(
                request = request,
                success = Consumer {
                    viewAppliedLists.value = it.data

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


    fun incrementCurrentPage() {
        currentPage.value = currentPage.value!! + 1
    }

    fun decrementCurrentPage() {
        currentPage.value = currentPage.value!! - 1
    }

    fun resetCurrentPage() {
        currentPage.value = 1
    }


}
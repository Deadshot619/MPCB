package com.gov.mpcb.menu_tabs.surprise_inspections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewAppliedListRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer

class SurpriseInspectionsViewModel : BaseViewModel<SurpriseInspectionsNavigator>() {

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    //Variable to hold [ViewAppliedListResponse] data
    private val viewAppliedLists = MutableLiveData<List<ViewAppliedListData>>()
    val _viewAppliedLists : LiveData<List<ViewAppliedListData>>
        get() = viewAppliedLists

    init {
        //Call this method only if network is available
        if (isNetworkAvailable())
            getAppliedListsData()
    }

    /**
     * This method calls the view_applied_list Api & sets the data to [viewAppliedLists]
     */
    fun getAppliedListsData() {
        val request = ViewAppliedListRequest().apply {
            userId = user.userId.toString()
        }

        mDisposable.add(
            DataProvider.getAppliedLists(
                request = request,
                success = Consumer {
                    viewAppliedLists.value = it.data
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }

}
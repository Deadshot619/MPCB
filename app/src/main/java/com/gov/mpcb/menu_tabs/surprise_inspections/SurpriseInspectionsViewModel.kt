package com.gov.mpcb.menu_tabs.surprise_inspections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ViewAppliedListRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.ViewAppliedListResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer

class SurpriseInspectionsViewModel : BaseViewModel<SurpriseInspectionsNavigator>() {

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    //Variable to hold [ViewAppliedListResponse] data
    private val viewAppliedLists = MutableLiveData<ViewAppliedListResponse>()
    val _viewAppliedLists : LiveData<ViewAppliedListResponse>
        get() = viewAppliedLists

    init {
        getAppliedListsData()
    }

    fun getAppliedListsData() {
        val request = ViewAppliedListRequest().apply {
            userId = user.userId.toString()
        }

        mDisposable.add(
            DataProvider.getAppliedLists(
                request = request,
                success = Consumer {
                    viewAppliedLists.value = it
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }

}
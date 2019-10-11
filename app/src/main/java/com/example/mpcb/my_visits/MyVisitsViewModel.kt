package com.example.mpcb.my_visits

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.MyVisitRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer

class MyVisitsViewModel : BaseViewModel<MyVisitsNavigator>() {

    private val visitList = MutableLiveData<ArrayList<MyVisitModel>>()

    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    fun getVisitList() = visitList

    fun getVisitListData() {
        val request = MyVisitRequest()
        request.userId = user.userId.toString()
        request.fromDate = "2017-09-01"
        request.toDate = "2019-09-30"
        dialogVisibility.value = true
        dialogMessage.value = "Fetching List..."
        mDisposable.add(DataProvider.getVisitList(request, Consumer {
            dialogVisibility.value = false
            visitList.value = it
        }, Consumer { checkError(it) }))

    }
}
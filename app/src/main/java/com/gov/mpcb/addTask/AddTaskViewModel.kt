package com.gov.mpcb.addTask

import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.response.UserListTaskResponse
import io.reactivex.functions.Consumer

class AddTaskViewModel : BaseViewModel<AddTaskNavigator>(){

    private var _userData = ArrayList<UserListTaskResponse>()
    val userData: ArrayList<UserListTaskResponse>
        get() = _userData

    /**
     * Method to fetch Users List Data for selection
     */
    fun fetchUsersListData(){
        mDisposable.add(
            DataProvider.getUserListTaskData(
                success =  Consumer {
                    it?.let{
                        _userData = it
                        mNavigator?.showAlert(it[0].name)
                    }
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }
}
package com.gov.mpcb.addTask

import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import io.reactivex.functions.Consumer

class AddTaskViewModel : BaseViewModel<AddTaskNavigator>(){


    /**
     * Method to fetch Users List Data for selection
     */
    fun fetchUsersListData(){
        mDisposable.add(
            DataProvider.getUserListTaskData(
                success =  Consumer {

                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }
}
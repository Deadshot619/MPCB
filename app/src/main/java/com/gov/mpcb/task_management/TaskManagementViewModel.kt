package com.gov.mpcb.task_management

import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.TaskDetailsRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import io.reactivex.functions.Consumer

class TaskManagementViewModel : BaseViewModel<TaskManagementNavigator>(){

    //Get User data from shared pref
    private val user by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    /**
     * Method to get Visit Lists data of the user
     */
    fun getTaskListData(){
        val request = TaskDetailsRequest().apply {
            userId = user.userId.toString()
        }

        dialogVisibility.value = true
        dialogMessage.value = "Fetching List..."

        mDisposable.add(DataProvider.getTaskDetails(
            request = request,
            success = Consumer {
                dialogVisibility.value = false

                if (it.status == 1 && it.data.isNotEmpty())
                    mNavigator?.submitRecyclerViewData(it)
                else if (it.status == 1 && it.data.isEmpty()){
                    mNavigator?.submitRecyclerViewData(it)
                    mNavigator?.showAlert(it.message)
                }else
                    mNavigator?.showAlert(it.message)

            },
            error = Consumer {
                checkError(it)
            }
        ))

    }
}
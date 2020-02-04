package com.gov.mpcb.addTask

import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.response.UserListTaskResponse
import io.reactivex.functions.Consumer

class AddTaskViewModel : BaseViewModel<AddTaskNavigator>(){

    //variable to store Users Data from [UserListTaskResponse]
    private var _userData = ArrayList<UserListTaskResponse>()
    val userData: ArrayList<UserListTaskResponse>
        get() = _userData

    //Variable to hold values of selected users
    private var _userAddedList = setOf<Int>()
    val userAddedList: Set<Int>
        get() = _userAddedList

    //Holds value of selected users temporarily
    val selectedUsersTemp = mutableSetOf<Int>()

    /**
     * Method to set text on User Details View
     */
    fun setText(noOfUsers: Int){
        mNavigator?.setText(noOfUsers)
    }

    /**
     * Method to add
     */
    fun addCheckedUserToListSet(list: Set<Int>){
        _userAddedList = list
    }

    /**
     * Method to fetch Users List Data for selection
     */
    fun fetchUsersListData(){
        mDisposable.add(
            DataProvider.getUserListTaskData(
                success =  Consumer {
                    it?.let{
                        _userData = it
//                        mNavigator?.showAlert(it[0].name)
                    }
                },
                error = Consumer {
                    checkError(it)
                }
            )
        )
    }


    fun clearViewModel(){
        super.onCleared()
    }
}
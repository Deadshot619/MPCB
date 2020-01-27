package com.gov.mpcb.task_management

import com.gov.mpcb.base.BaseNavigator
import com.gov.mpcb.network.response.TaskDetailsResponse

interface TaskManagementNavigator : BaseNavigator {

    //Method to submit data to recycler view
    fun submitRecyclerViewData(taskDetailsResponse: TaskDetailsResponse)

    //Method to show alert
    fun showAlert(message: String)

}
package com.gov.mpcb.addTask

import com.gov.mpcb.base.BaseNavigator

interface AddTaskNavigator: BaseNavigator {
    fun showAlert(message: String)
}
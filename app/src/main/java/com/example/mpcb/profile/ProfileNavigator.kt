package com.example.mpcb.profile

import com.example.mpcb.base.BaseNavigator


interface ProfileNavigator : BaseNavigator {

    fun onNameError()

    fun onEmailError()

    fun onMobileError()

    fun onUpdateProfileSuccess(message: String)
}
package com.gov.mpcb.login

import com.gov.mpcb.base.BaseNavigator


interface LoginNavigator : BaseNavigator {
    fun onUsernameError()

    fun onPasswordError()

    fun onloginSuccess()

    fun onErrorOccured(message: String)
}
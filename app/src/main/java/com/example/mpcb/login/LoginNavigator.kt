package com.example.mpcb.login

import com.example.mpcb.base.BaseNavigator


interface LoginNavigator : BaseNavigator {
    fun onUsernameError()

    fun onPasswordError()

    fun onloginSuccess()

    fun onErrorOccured(message: String)
}
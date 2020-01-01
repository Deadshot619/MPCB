package com.gov.mpcb.profile

import com.gov.mpcb.base.BaseNavigator


interface ProfileNavigator : BaseNavigator {

    fun onNameError()

    fun onEmailError()

    fun onMobileError()

    fun onCurrentPwdError()

    fun onNewPwdError()

    fun onUpdateProfileSuccess(message: String)

    fun onChangePwdSuccess(msg:String)

    fun onValidationError(message: String)
}
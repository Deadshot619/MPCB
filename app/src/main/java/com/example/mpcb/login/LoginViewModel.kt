package com.example.mpcb.login

import android.text.TextUtils
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.utils.md5
import io.reactivex.functions.Consumer


class LoginViewModel : BaseViewModel<LoginNavigator>() {

    fun onLoginClick(loginRequest: LoginRequest) {
        when {
            TextUtils.isEmpty(loginRequest.userName) -> {
                dialogMessage.value = "Loading..."
                dialogVisibility.value = true
                mNavigator!!.onUsernameError()
            }
            TextUtils.isEmpty(loginRequest.password) -> {
                mNavigator!!.onPasswordError()
            }
            else -> login(loginRequest)
        }
    }

    private fun login(loginRequest: LoginRequest) {
        val reqModel = LoginRequest()
        reqModel.userName = loginRequest.userName
        reqModel.password = loginRequest.password.md5()
        dialogMessage.value = "Loading..."
        dialogVisibility.value = true
        mDisposable.add(DataProvider.login(reqModel, Consumer {
            dialogVisibility.value = false
            mNavigator!!.onloginSuccess()
        }, Consumer { checkError(it) }))
    }


}
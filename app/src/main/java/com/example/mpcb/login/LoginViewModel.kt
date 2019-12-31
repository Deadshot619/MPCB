package com.example.mpcb.login

import android.text.TextUtils
import android.util.Log
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.constants.Constants.Companion.FIREBASE_TOKEN
import com.example.mpcb.utils.md5
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import io.reactivex.functions.Consumer


class LoginViewModel : BaseViewModel<LoginNavigator>() {

    private val _TAG = "Firebase Token"

    val loginRequest = LoginRequest()

    fun onLoginClick() {
        when {
            TextUtils.isEmpty(loginRequest.userName) -> {
                mNavigator!!.onUsernameError()
            }
            TextUtils.isEmpty(loginRequest.password) -> {
                mNavigator!!.onPasswordError()
            }
            else -> getFirebaseInstanceId(loginRequest)
        }
    }

    private fun login(
        loginRequest: LoginRequest,
        token: String
    ) {
        if (token != null && token != ""){
            val reqModel = LoginRequest().apply {
                userName = loginRequest.userName
                password = loginRequest.password.md5()
                deviceToken = token
            }

            dialogMessage.value = "Loading..."
            dialogVisibility.value = true
            mDisposable.add(DataProvider.login(
                reqModel,
                Consumer {
                    dialogVisibility.value = false
                    PreferencesHelper.setLogin(true)
                    PreferencesHelper.setPreferences(Constants.USER, Gson().toJson(it))
                    mNavigator!!.onloginSuccess()
                }, Consumer { checkError(it) }))
        } else {
            mNavigator?.onErrorOccured("Could not fetch token.")
        }
    }

    /**
     * Method to get Firebase token ID for this device
     */
    private fun getFirebaseInstanceId(loginRequest: LoginRequest) {
//        dialogMessage.value = "Loading..."
//        dialogVisibility.value = true

        try {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(_TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    //If token is not null, then save it in Shared Pref.
                    if (token != null){
                        login(loginRequest, token)
                        PreferencesHelper.setStringPreference(FIREBASE_TOKEN, token)
                    }else{
                        mNavigator?.onErrorOccured("Could not fetch token.")
                    }
//
//                token?.let {
//                }

                    // Log and toast
                    Log.d(_TAG, "Token : $token")
                })
        }finally {
//            dialogVisibility.value = false
        }
    }

}
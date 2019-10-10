package com.example.mpcb.profile

import android.text.TextUtils
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.UpdateProfileRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer


class ProfileViewModel : BaseViewModel<ProfileNavigator>() {


    fun getUserModel(): UpdateProfileRequest {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        val userModel = Gson().fromJson(user, LoginResponse::class.java)
        val updateProfile = UpdateProfileRequest()
        updateProfile.userId = userModel.userId.toString()
        updateProfile.name = userModel.name ?: ""
        updateProfile.email = userModel.email ?: ""
        updateProfile.phone = userModel.mobile ?: ""
        return updateProfile
    }

    fun onUpdateClick(request: UpdateProfileRequest) {
        if (TextUtils.isEmpty(request.name)) {
            mNavigator!!.onNameError()
        } else if (TextUtils.isEmpty(request.email)) {
            mNavigator!!.onEmailError()
        } else if (TextUtils.isEmpty(request.phone)) {
            mNavigator!!.onMobileError()
        } else
            updateProfile(request)
    }

    private fun updateProfile(request: UpdateProfileRequest) {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        val userModel = Gson().fromJson(user, LoginResponse::class.java)
        request.userId = userModel.userId.toString()
        dialogMessage.value = "Updating Profile..."
        dialogVisibility.value = true
        mDisposable.add(DataProvider.updateProfile(request, Consumer {
            dialogVisibility.value = false
            mNavigator!!.onUpdateProfileSuccess(it.message)
        }, Consumer { checkError(it) }))
    }

}
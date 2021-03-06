package com.gov.mpcb.profile

import android.text.TextUtils
import com.google.gson.Gson
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.ChangePwdRequest
import com.gov.mpcb.network.request.UpdateProfileRequest
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import com.gov.mpcb.utils.validations.isEmailValid
import com.gov.mpcb.utils.validations.isValidMobile
import io.reactivex.functions.Consumer


class ProfileViewModel : BaseViewModel<ProfileNavigator>() {

    private lateinit var userData: LoginResponse

    fun getUserModel(): UpdateProfileRequest {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        userData = Gson().fromJson(user, LoginResponse::class.java)
        val updateProfile = UpdateProfileRequest()
        updateProfile.userId = userData.userId.toString()
        updateProfile.name = userData.name ?: ""
        updateProfile.email = userData.email ?: ""
        updateProfile.phone = userData.mobile ?: ""
        return updateProfile
    }

    fun onUpdateClick(request: UpdateProfileRequest) {
        when {
            TextUtils.isEmpty(request.name) -> mNavigator?.onNameError()
            TextUtils.isEmpty(request.email) -> mNavigator?.onEmailError()
            TextUtils.isEmpty(request.phone) -> mNavigator?.onMobileError()
            !isEmailValid(request.email) -> mNavigator?.onValidationError("Invalid Email ID")
            !isValidMobile(request.phone) -> mNavigator?.onValidationError("Invalid Number")
            else -> updateProfile(request)
        }
    }

    private fun updateProfile(request: UpdateProfileRequest) {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        val userModel = Gson().fromJson(user, LoginResponse::class.java)
        request.userId = userModel.userId.toString()
        dialogMessage.value = "Updating Profile..."
        dialogVisibility.value = true
        mDisposable.add(DataProvider.updateProfile(request, Consumer {
            dialogVisibility.value = false
            userData.name = request.name
            userData.email = request.email
            userData.mobile = request.phone
            PreferencesHelper.setPreferences(Constants.USER, Gson().toJson(userData))
            mNavigator!!.onUpdateProfileSuccess(it.message)
        }, Consumer { checkError(it) }))
    }

    fun onChangePwd(request: ChangePwdRequest) {
        when {
            TextUtils.isEmpty(request.currentPwd) -> mNavigator!!.onCurrentPwdError()
            TextUtils.isEmpty(request.currentPwd) -> mNavigator!!.onNewPwdError()
            else -> changePwd(request)
        }
    }

    private fun changePwd(request: ChangePwdRequest) {
        request.userId = userData.userId.toString()
        dialogMessage.value = "Changing Pwd..."
        dialogVisibility.value = true
        mDisposable.add(DataProvider.changePassword(request, Consumer {
            dialogVisibility.value = false
           // mNavigator!!.onUpdateProfileSuccess(it.message)
            if (it.status == "1")
                mNavigator?.onChangePwdSuccess(it.message)
            else
                mNavigator?.onUpdateProfileSuccess(it.message)
        }, Consumer { checkError(it) }))
    }

}
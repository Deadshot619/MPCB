package com.gov.mpcb.login

import android.content.Context
import android.content.Intent
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivityLoginBinding
import com.gov.mpcb.home.HomeActivity
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import com.gov.mpcb.utils.showMessage
import java.util.*

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override fun getLayoutId() = R.layout.activity_login
    override fun getViewModel() = LoginViewModel::class.java
    override fun getNavigator() = this@LoginActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    companion object {
        fun getCallingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onUsernameError() = showMessage("Enter Username")
    override fun onPasswordError() = showMessage("Enter Password")
    override fun onErrorOccured(message: String) = showMessage(message)

    override fun onloginSuccess() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finish()
    }

    override fun onBinding() {
//        val login = LoginRequest()
//        login.userName = "fo_s1nm"
//        login.password = "Admin@123"

        mBinding.viewModel = mViewModel
        mBinding.model = mViewModel.loginRequest

//        mBinding.model.apply {
//            userName = "fo_s1nm"
//            password = "Admin@123"
//        }

        //Set welcome text
        mBinding.welcomeMsgTwo.text = getWelcomeText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))

        //Indicates that the app is launched for first time.
        PreferencesHelper.setAppLaunchFirstTime()
    }

    /**
     * This method is used to set the Welcome Text according to current time.
     */
    private fun getWelcomeText(hour: Int): String{
        return when (hour) {
            in 5..11 -> "Good Morning"
            in 12..17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }
}

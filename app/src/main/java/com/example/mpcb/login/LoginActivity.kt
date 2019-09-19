package com.example.mpcb.login

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.databinding.ActivityLoginBinding
import com.example.mpcb.home.HomeActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.log.LogHelper
import com.example.mpcb.utils.showMessage

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override fun getLayoutId() = R.layout.activity_login
    override fun getViewModel() = LoginViewModel::class.java
    override fun getNavigator() = this@LoginActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    companion object {
        fun getCallingIntent(context: Context) = Intent(context,LoginActivity::class.java)
    }

    override fun onBinding() {
        mBinding.btnLogin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

        val usr = mPref.getPreferences(Constants.mloginUserKey,"")
        val status = mPref.isLogin()

        LogHelper.showLogData("username: ${usr}   status: ${status}")
    }

}

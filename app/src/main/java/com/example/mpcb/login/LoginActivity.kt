package com.example.mpcb.login

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.databinding.ActivityLoginBinding
import com.example.mpcb.home.HomeActivity
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.log.LogHelper
import com.example.mpcb.utils.showMessage
import io.reactivex.functions.Consumer

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(), LoginNavigator {

    override fun getLayoutId() = R.layout.activity_login
    override fun getViewModel() = LoginViewModel::class.java
    override fun getNavigator() = this@LoginActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    companion object {
        fun getCallingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun onBinding() {
        mBinding.btnLogin.setOnClickListener {
            /*val req = LoginRequest()
            req.userName = "fo_s1nm"
            req.password = "0e7517141fb53f21ee439b355b5a1d0a"
            DataProvider.login(req, Consumer {
                Log.e("data", it.toString())
            }, Consumer {
                Log.e("data", it.message)
            })*/
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

        val usr = mPref.getPreferences(Constants.mloginUserKey,"")
        val status = mPref.isLogin()

        LogHelper.showLogData("username: ${usr}   status: ${status}")
    }

}

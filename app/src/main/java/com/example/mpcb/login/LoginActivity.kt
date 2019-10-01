package com.example.mpcb.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.base.IntentNavigator
import com.example.mpcb.databinding.ActivityLoginBinding
import com.example.mpcb.home.HomeActivity
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.dialoug.DialogueHelper
import com.example.mpcb.utils.log.LogHelper
import com.example.mpcb.utils.showMessage
import com.example.mpcb.visit_report.ReportsPageActivity
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

    override fun onUsernameError() = showMessage("Enter Username")
    override fun onPasswordError() = showMessage("Enter Password")

    override fun onloginSuccess() {
        startActivity(Intent(this@LoginActivity, ReportsPageActivity::class.java))
        finish()
    }

    override fun onBinding() {
        //mBinding.model = LoginRequest()
        //mBinding.viewModel = mViewModel



    }


}

package com.example.mpcb.splash

import android.content.Intent
import android.os.Handler
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.base.BaseNavigator
import com.example.mpcb.base.IntentNavigator
import com.example.mpcb.databinding.ActivitySplashBinding
import com.example.mpcb.login.LoginActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.permission.PermissionUtils
import com.example.mpcb.utils.permission.PermissionUtils.Companion.STORAGE_PERMISSIONS
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage
import com.futuregroup.kotlintest.splash.SplashNavigator

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    private val handler = Handler()

    override fun getLayoutId() = R.layout.activity_splash
    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java
    override fun getNavigator(): BaseNavigator = this@SplashActivity
    override fun onInternetError() {}
    override fun onError(message: String) = showMessage(message)

    override fun onBinding() {
        handler.postDelayed({
            IntentNavigator.navigateToLoginActivity(this)
            mPref.setLogin(true)
            mPref.setPreferences(Constants.mloginUserKey,"ABCD")
        }, 20000)


    }


}

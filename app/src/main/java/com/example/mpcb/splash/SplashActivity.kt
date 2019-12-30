package com.example.mpcb.splash

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.base.BaseNavigator
import com.example.mpcb.base.IntentNavigator
import com.example.mpcb.databinding.ActivitySplashBinding
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage
import com.futuregroup.kotlintest.splash.SplashNavigator
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    private val _TAG = this@SplashActivity.toString()

    override fun getLayoutId() = R.layout.activity_splash
    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java
    override fun getNavigator(): BaseNavigator = this@SplashActivity

    //Lazily create a Update Dialog
    private val showUpdateDialog by lazy {
        AlertDialog.Builder(this).apply {
            setTitle("Update Available")
            setMessage("Please update the app to continue!")
            setPositiveButton("Update") { _, _ ->
                navigateToNextScreen()
            }
            setNegativeButton("Cancel") { _, _ ->
                finish()
            }
            setCancelable(false)
        }.create()
    }

    override fun showAlert(message: String) {
        showMessage(message)
    }

    override fun navigateToNextScreen() {
        if (PreferencesHelper.isLogin()) {
            getFirebaseInstanceId()
            IntentNavigator.navigateToHomeActivity(this)
        } else {
            IntentNavigator.navigateToLoginActivity(this)
        }
        finish()
    }

    override fun showUpdateDialog() {
        showUpdateDialog.show()
    }

    override fun onInternetError() {}
    override fun onError(message: String) = showMessage(message)

    override fun onBinding() {

        //Check the version of current app
        mViewModel.getAppVersion()

    }

    private fun getFirebaseInstanceId(){
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(_TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
//                val msg = getString(R.string.msg_token_fmt, token)
                Log.d(_TAG, "Token : $token")
                Toast.makeText(baseContext, "$token", Toast.LENGTH_SHORT).show()
            })
    }
}

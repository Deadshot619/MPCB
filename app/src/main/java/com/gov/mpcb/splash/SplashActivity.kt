package com.gov.mpcb.splash

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.futuregroup.kotlintest.splash.SplashNavigator
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.base.BaseNavigator
import com.gov.mpcb.base.IntentNavigator
import com.gov.mpcb.databinding.ActivitySplashBinding
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import com.gov.mpcb.utils.showMessage


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    private val _TAG = this@SplashActivity.toString()

    override fun getLayoutId() = R.layout.activity_splash
    override fun getViewModel(): Class<SplashViewModel> = SplashViewModel::class.java
    override fun getNavigator(): BaseNavigator = this@SplashActivity

    override fun showAlert(message: String) {
        showMessage(message)
    }

    //Lazily create a Update Dialog
    private val showUpdateDialog by lazy {
        AlertDialog.Builder(this).apply {
            setTitle("Update Available")
            setMessage("There's an update available for the app!")
            setPositiveButton("Update") { _, _ ->
//                navigateToNextScreen()
                goToPlayStore()
            }
            setNegativeButton("Later") { _, _ ->
                navigateToNextScreen()
            }
            setCancelable(false)
        }.create()
    }

    //Lazily create a Force Update Dialog
    private val showForceUpdateDialog by lazy {
        AlertDialog.Builder(this).apply {
            setTitle("Update Available")
            setMessage("Please update the app to continue!")
            setPositiveButton("Update") { _, _ ->
                goToPlayStore()
            }
            setNegativeButton("Cancel") { _, _ ->
                finish()
            }
            setCancelable(false)
        }.create()
    }


    override fun navigateToNextScreen() {
        if (PreferencesHelper.isLogin()) {
            IntentNavigator.navigateToHomeActivity(this)
        } else {
            //get Firebase instance ID before logging in.
//            getFirebaseInstanceId()
            IntentNavigator.navigateToLoginActivity(this)
        }
        finish()
    }

    /**
     * Method to show Update dialog
     * when true show Force Update dialog,
     * else show a cancelable update dialog.
     */
    override fun showUpdateDialog(flag: Boolean) {
        if (flag)
            showForceUpdateDialog.show()
        else
            showUpdateDialog.show()
    }

    override fun onInternetError() {}
    override fun onError(message: String) = showMessage(message)

    override fun onBinding() {

        //Check the version of current app
        mViewModel.getAppVersion()

    }

    /**
     * Method to get Firebase token ID for this device
     */
//    private fun getFirebaseInstanceId(){
//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w(_TAG, "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//
//                //If token is not null, then save it in Shared Pref.
//                token?.let {
//                    PreferencesHelper.setStringPreference(FIREBASE_TOKEN, token)
//                }
//
//                // Log and toast
//                Log.d(_TAG, "Token : $token")
//                Toast.makeText(baseContext, "$token", Toast.LENGTH_SHORT).show()
//            })
//    }

    /***
     * Method to redirect app to playstore.
     */
    private fun goToPlayStore() {
        val playStoreMarketUrl = "market://details?id="
        val playStoreWebUrl = "https://play.google.com/store/apps/details?id="
        val packageName = applicationContext.packageName
        try {
            var intent = applicationContext.packageManager
                .getLaunchIntentForPackage("com.android.vending")
            if (intent != null) {
                val androidComponent = ComponentName(
                    "com.android.vending",
                    "com.google.android.finsky.activities.LaunchUrlHandlerActivity"
                )
                intent.component = androidComponent
                intent.data = Uri.parse(playStoreMarketUrl + packageName)
            } else {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreMarketUrl + packageName))
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreWebUrl + packageName))
            startActivity(intent)
        }

    }
}

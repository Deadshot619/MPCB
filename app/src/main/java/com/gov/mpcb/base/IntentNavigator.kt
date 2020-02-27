package com.gov.mpcb.base

import android.content.Context
import com.gov.mpcb.home.HomeActivity
import com.gov.mpcb.login.LoginActivity


object IntentNavigator {
    fun navigateToLoginActivity(context: Context) {
        val intent = LoginActivity.getCallingIntent(context)
        context.startActivity(intent)
    }

    fun navigateToHomeActivity(context: Context) {
        val intent = HomeActivity.getCallingIntent(context)
        context.startActivity(intent)
    }
}








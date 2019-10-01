package com.example.mpcb.base

import android.content.Context
import com.example.mpcb.home.HomeActivity
import com.example.mpcb.login.LoginActivity


object IntentNavigator {
    fun navigateToLoginActivity(context: Context) {
        val intent = LoginActivity.getCallingIntent(context)
        context.startActivity(intent)
    }

    fun navigateToDashboardActivity(context: Context){
        val intent = HomeActivity.getCallingIntent(context)
        context.startActivity(intent)
    }

}








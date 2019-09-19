package com.example.mpcb.base

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.example.mpcb.login.LoginActivity
import com.example.mpcb.utils.constants.Constants


    object IntentNavigator{

        fun navigateToLoginActivity(context:Context){
            val intent = LoginActivity.getCallingIntent(context)
            context.startActivity(intent)
        }

    }








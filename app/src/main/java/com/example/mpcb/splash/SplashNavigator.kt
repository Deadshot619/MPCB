package com.futuregroup.kotlintest.splash

import com.example.mpcb.base.BaseNavigator


interface SplashNavigator : BaseNavigator{

    fun showAlert(message: String)

    fun navigateToNextScreen()

    fun showUpdateDialog()

}
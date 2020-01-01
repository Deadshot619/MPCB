package com.futuregroup.kotlintest.splash

import com.gov.mpcb.base.BaseNavigator


interface SplashNavigator : BaseNavigator{

    fun showAlert(message: String)

    fun navigateToNextScreen()

    fun showUpdateDialog(flag: Boolean)

}
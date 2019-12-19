package com.example.mpcb.splash

import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.AppVersionRequest
import com.futuregroup.kotlintest.splash.SplashNavigator
import io.reactivex.functions.Consumer


class SplashViewModel : BaseViewModel<SplashNavigator>(){


    /**
     * Method to get the current version of App.
     */
    fun getAppVersion(){

        mDisposable.add(
            DataProvider.checkCurrentVersion(
                request = AppVersionRequest(),
                success = Consumer {
                    //If a new version is available then show a prompt to user saying 'update is available
                    if (it.status == 1) {
                        //TODO 19/12/2019 : Show a alert dialog when 'force' update is available
                        //which prevents the user from using the app until he updates it.
                        mNavigator?.showAlert(it.message + ": Update is Available")
                    }else {
                        mNavigator?.showAlert(it.message + ": Update is not Available")
                        //Move this to above if condition
//                        mNavigator?.showUpdateDialog()
                        mNavigator?.navigateToNextScreen()
                    }
                },
                error = Consumer { checkError(it) }
            )
        )
    }
}
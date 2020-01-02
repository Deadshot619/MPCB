package com.gov.mpcb.base

interface BaseNavigator {

    fun onInternetError()

    fun onError(message: String)
}
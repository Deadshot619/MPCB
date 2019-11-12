package com.example.mpcb.base

interface BaseNavigator {

    fun onInternetError()

    fun onError(message: String)
}
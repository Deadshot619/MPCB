package com.gov.mpcb.utils

import com.gov.mpcb.base.MPCBApp

enum class LoadingStatus{ LOADING, ERROR, DONE }

object CommonUtils {
    //Get Device's Display Metrics
    private val displayMetrics = MPCBApp.instance.resources.displayMetrics

    //Get Device's height in dp
    val dpHeight: Float
        get() = displayMetrics.heightPixels / displayMetrics.density

    //Get Device's width in dp
    val dpWidth: Float
        get() =  displayMetrics.widthPixels / displayMetrics.density

}
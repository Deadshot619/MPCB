package com.example.mpcb.base

import com.example.mpcb.network.response.CheckInfoModel

@FunctionalInterface
interface CheckInCallBack {

    fun getCheckInfo(): CheckInfoModel
}
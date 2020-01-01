package com.gov.mpcb.base

import com.gov.mpcb.network.response.CheckInfoModel

@FunctionalInterface
interface CheckInCallBack {

    fun getCheckInfo(): CheckInfoModel
}
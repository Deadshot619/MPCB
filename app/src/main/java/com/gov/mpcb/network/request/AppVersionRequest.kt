package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.mpcb.BuildConfig

class AppVersionRequest {

    @SerializedName("app_type")
    @Expose
    var appType: String = "android"    //android or iOS

    @SerializedName("version")
    @Expose
    var version: String = BuildConfig.VERSION_NAME
}
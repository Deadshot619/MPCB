package com.example.mpcb.network.request

import com.example.mpcb.BuildConfig
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AppVersionRequest {

    @SerializedName("app_type")
    @Expose
    var appType: String = "android"    //android or iOS

    @SerializedName("version")
    @Expose
    var version: String = BuildConfig.VERSION_CODE.toString()
}
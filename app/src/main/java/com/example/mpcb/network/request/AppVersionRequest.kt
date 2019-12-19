package com.example.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AppVersionRequest {

    @SerializedName("app_type")
    @Expose
    var appType: String = ""    //android or iOS

    @SerializedName("version")
    @Expose
    var version: String = ""
}
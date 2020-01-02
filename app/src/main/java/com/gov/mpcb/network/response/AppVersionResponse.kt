package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AppVersionResponse(

    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: AppVersionData

)


data class AppVersionData(

    @Expose @SerializedName("force_update") var forceUpdate: Boolean = false

)
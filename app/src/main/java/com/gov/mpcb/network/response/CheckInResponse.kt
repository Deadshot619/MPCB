package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckInResponse {

    @SerializedName("status")
    @Expose
    var status: Boolean = false
    @SerializedName("message")
    @Expose
    var message: String = ""
}

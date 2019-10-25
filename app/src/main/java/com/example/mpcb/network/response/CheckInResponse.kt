package com.example.mpcb.network.response

import com.google.gson.annotations.SerializedName

class CheckInResponse {

    @SerializedName("status")
    var status: Boolean = false
    @SerializedName("message")
    var message: String = ""
}

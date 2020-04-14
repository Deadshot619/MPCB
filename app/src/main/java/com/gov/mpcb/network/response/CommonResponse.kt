package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommonResponse (
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("message") val message: String = ""
)
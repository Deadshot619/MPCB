package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReportSubmitResponse(
    @Expose @SerializedName("status") val status: Boolean,
    @Expose @SerializedName("message") var message: String = ""
)

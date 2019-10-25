package com.example.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReportSubmitResponse(
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("message") var message: String = ""
)

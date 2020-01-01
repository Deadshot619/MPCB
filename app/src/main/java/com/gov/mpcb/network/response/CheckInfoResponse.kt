package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckInfoResponse(

    @Expose @SerializedName("status") var status: Boolean ,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: CheckInfoModel


) {
}
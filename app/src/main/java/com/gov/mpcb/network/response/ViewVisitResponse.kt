package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.mpcb.network.request.Data

data class ViewVisitResponse (
    @Expose @SerializedName("status") var status: Boolean,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: Data
){

}
package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyVisitResponse(
    @Expose @SerializedName("status")  var status: String = "",
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: ArrayList<MyVisitModel>

)

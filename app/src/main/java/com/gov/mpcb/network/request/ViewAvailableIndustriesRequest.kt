package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ViewAvailableIndustriesRequest {
    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("UserId")
    var userId = ""

    @Expose
    @SerializedName("page")
    var page = 1

    @Expose
    @SerializedName(value = "search_string", alternate = ["search_industry"])
    var searchString = ""
}
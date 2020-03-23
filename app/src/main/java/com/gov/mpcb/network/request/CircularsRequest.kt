package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CircularsRequest {
    @Expose
    @SerializedName("page")
    var page = 0

    @Expose
    @SerializedName("search")
    var search = ""
}
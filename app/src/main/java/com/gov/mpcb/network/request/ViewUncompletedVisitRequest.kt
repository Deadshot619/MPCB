package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ViewUncompletedVisitRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("UserId")
    var userId = ""

    //Should be in MM-YYYY format
    @Expose
    @SerializedName("prev_month")
    var previousMonth = ""
}
package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UncompletedVisitRemarkRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("UserId")
    var userId = ""

    @Expose
    @SerializedName("visit_id")
    var visitId = ""

    @Expose
    @SerializedName("remarks")
    var remark = ""
}
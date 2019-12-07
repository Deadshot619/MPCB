package com.example.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ViewVisitRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("visitId")
    var visitId: Long = 0

    @SerializedName("indus_imis_id")
    @Expose
    var indusImisId: String = ""

    @Expose
    @SerializedName("UserId")
    var userId = ""
}
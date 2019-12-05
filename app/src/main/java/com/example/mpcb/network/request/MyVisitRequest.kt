package com.example.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyVisitRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""
    @Expose
    @SerializedName("UserId")
    var userId = ""

    @Expose
    @SerializedName("visitId")
    var visitId = ""

    @Expose
    @SerializedName("from_date")
    var fromDate = ""
    @Expose
    @SerializedName("to_date")
    var toDate = ""

}

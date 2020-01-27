package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TaskDetailsRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("UserId")
    var userId = ""

}
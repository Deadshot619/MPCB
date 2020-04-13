package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class IdOtherDocumentsRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("applicant_id")
    var applicantId = 0

    @Expose
    @SerializedName("type")
    var type: String = ""
}
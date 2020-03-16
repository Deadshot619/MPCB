package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ViewPreviousInspectionListRequest {
    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("industry_iin")
    var industryIin = ""
}
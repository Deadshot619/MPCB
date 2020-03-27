package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ViewIndustryDirectoryDataRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = "12"

    @Expose
    @SerializedName("industry_id")
    var industryId: Int = 0

    @Expose
    @SerializedName("type")
    var type: String = ""
}
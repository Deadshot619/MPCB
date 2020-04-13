package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.mpcb.utils.IndustryDirectoryType

class ViewIndustryDirectoryDataRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = "12"

    @Expose
    @SerializedName("industry_id")
    var industryId: Int = 0

    @Expose
    @SerializedName("type")
    private var type: String = ""

    //Using a setter & a enum allows user to only select value of the enum constants
    var industryDirectoryType: IndustryDirectoryType = IndustryDirectoryType.Consent
        set(value) {
            type = value.value
            field = value
        }
}
package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashboardDataRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""
    @Expose
    @SerializedName("UserId")
    var userId = ""
    @Expose
    @SerializedName("from_date")
    var fromDate = ""
    @Expose
    @SerializedName("to_date")
    var toDate = ""
    @Expose
    @SerializedName("get_jurisdiction_stat")
    var jurisdictionStat = 0

}

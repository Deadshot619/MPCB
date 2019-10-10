package com.example.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DashboardDataResponse {

    @Expose
    @SerializedName("status")
    var status = ""
    @Expose
    @SerializedName("total_visits")
    var totalVisit = 0
    @Expose
    @SerializedName("completed_visit")
    var completedVisit = 0
    @Expose
    @SerializedName("pending_visit")
    var pendingVisit = 0
    @Expose
    @SerializedName("visited_on_time")
    var visitedOnTime = 0
    @Expose
    @SerializedName("report_uploaded_on_time")
    var reportedUploadedOnTime = 0
    @SerializedName("message")
    var message = ""

}

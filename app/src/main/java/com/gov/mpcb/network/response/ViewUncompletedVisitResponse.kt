package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewUncompletedVisitResponse(
    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("uncompleted_visit_present") var isUncompletedVisitPresent: Int = 0,
    @Expose @SerializedName("uncompleted_visit_no") var totalUncompletedVisit: Int,
    @Expose @SerializedName("data") var data: List<MyVisitModel> = listOf()
)
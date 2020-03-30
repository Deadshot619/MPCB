package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.mpcb.network.request.Data

data class ViewIndustryDirectoryDataResponse(
    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("indus_data") var industryData: IdIndustryData,
    @Expose @SerializedName("data") var data: Data
)

data class IdIndustryData(
    @Expose @SerializedName("Address") val address: String,
    @Expose @SerializedName("Email") val emailId: String,
    @Expose @SerializedName("IndustryName") val industryName: String,
    @Expose @SerializedName("TelNo") val telNo: String
)


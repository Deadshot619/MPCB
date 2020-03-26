package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewDirectoryListData(
    @Expose @SerializedName("address") val address: String,
    @Expose @SerializedName("email") val emailId: String,
    @Expose @SerializedName("industry_id") val industryId: Int,
    @Expose @SerializedName("industryname") val industryName: String
)
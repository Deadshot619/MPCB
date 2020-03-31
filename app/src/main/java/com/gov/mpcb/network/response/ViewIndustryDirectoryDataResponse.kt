package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewIndustryDirectoryDataResponse<T>(
    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("indus_data") var industryData: IdIndustryData,
    @Expose @SerializedName("data") var data: List<T>
)

data class IdIndustryData(
    @Expose @SerializedName("Address") val address: String,
    @Expose @SerializedName("Email") val emailId: String,
    @Expose @SerializedName("IndustryName") val industryName: String,
    @Expose @SerializedName("TelNo") val telNo: String
)

data class IdConsentData(
    @Expose @SerializedName("Application_id") val Application_id: Int,
    @Expose @SerializedName("Approval_date") val Approval_date: String,
    @Expose @SerializedName("address") val address: String,
    @Expose @SerializedName("applicant_id") val applicant_id: Int,
    @Expose @SerializedName("industryname") val industryname: String,
    @Expose @SerializedName("outward_number") val outward_number: String,
    @Expose @SerializedName("payment_for") val payment_for: Int,
    @Expose @SerializedName("payment_received") val payment_received: Int,
    @Expose @SerializedName("payment_received_on") val payment_received_on: String,
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("type") val type: String,
    @Expose @SerializedName("unique_id") val unique_id: String,
    @Expose @SerializedName("validitydate") val validitydate: String,
    @Expose @SerializedName("view_link") val view_link: String
)

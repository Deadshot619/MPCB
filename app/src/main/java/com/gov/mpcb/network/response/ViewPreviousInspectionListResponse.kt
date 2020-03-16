package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewPreviousInspectionListResponse(
    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: List<ViewPreviousInspectionListData> = listOf()
)

data class ViewPreviousInspectionListData(
    @Expose @SerializedName("assigned_to_officer") val assigned_to_officer: String,
    @Expose @SerializedName("download_link") val download_link: String,
    @Expose @SerializedName("fo_id") val fo_id: Int,
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("industry_imis_id") val industry_imis_id: String,
    @Expose @SerializedName("is_surprise_inspection") val is_surprise_inspection: Int,
    @Expose @SerializedName("schduled_on") val schduled_on: String,
    @Expose @SerializedName("visited_on") val visited_on: String
)
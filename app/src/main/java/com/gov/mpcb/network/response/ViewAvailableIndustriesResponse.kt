package com.gov.mpcb.network.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ViewAvailableIndustriesResponse<T>(
    @Expose @SerializedName("data") val data: List<T> = listOf(),
    @Expose @SerializedName("message") val message: String = "",
    @Expose @SerializedName("status") val status: Int = -1,
    @Expose @SerializedName("total_rows") val total_rows: Int = 0
)

@Parcelize
data class ViewAvailableIndustriesData(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("industry_address") val industry_address: String,
    @Expose @SerializedName("industry_iin") val industry_iin: String,
    @Expose @SerializedName("industry_name") val industry_name: String,
    @Expose @SerializedName("industry_type_name") val industry_type_name: String,
    @Expose @SerializedName("is_cac") val is_cac: Int,
    @Expose @SerializedName("is_cc") val is_cc: Int,
    @Expose @SerializedName("is_sugar") val is_sugar: Int,
    @Expose @SerializedName("pincode") val pincode: String
): Parcelable
package com.example.mpcb.network.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyVisitModel(
    @Expose @SerializedName("visit_scheduler_id") var visitSchedulerId: Long = 0,
    @Expose @SerializedName("industry_imis_id") var industryIMISId: String = "",
    @Expose @SerializedName("industry_name") var industryName: String = "",
    @Expose @SerializedName("industry_address") var industryAddress: String = "",
    @Expose @SerializedName("schduled_on") var scheduledOn: String? = null,
    @Expose @SerializedName("visited_on") var visitedOn: String? = null,
    @Expose @SerializedName("report_submitted_on") var reportSubmittedOn: String? = null,
    @Expose @SerializedName("compliance_status") var complianceStatus: String = "",
    @Expose @SerializedName("visit_satus") var visitStatus: String = "",
    @Expose @SerializedName("check_in_status") var checkInStatus: Int = -1,
    @Expose @SerializedName("jvs_details_uploaded") var jvsDetailsUploaded: String = "",
    @Expose @SerializedName("latitude") var latitude: String? = "",
    @Expose @SerializedName("longitude") var longitude: String? = "",
    @Expose @SerializedName("selfie_img") var selfieImage: String? = "",
    @Expose @SerializedName("checked_in_date") var checkInDate: String? = ""



) : Parcelable

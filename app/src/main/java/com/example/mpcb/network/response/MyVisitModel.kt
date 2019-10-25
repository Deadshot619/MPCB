package com.example.mpcb.network.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyVisitModel(
    @Expose @SerializedName("visit_scheduler_id") var visitSchedulerId: Long = 0,
    @Expose @SerializedName("industry_imis_id") var industryIMISId: String = "",
    @Expose @SerializedName("industry_name") var industryName: String = "",
    @Expose @SerializedName("industry_address") var industryAddress: String = "",
    @Expose @SerializedName("schduled_on") var scheduledOn: String = "",
    @Expose @SerializedName("visited_on") var visitedOn: String = "",
    @Expose @SerializedName("report_submitted_on") var reportSubmittedOn: String = "",
    @Expose @SerializedName("compliance_status") var complianceStatus: String = "",
    @Expose @SerializedName("visit_satus") var visitStatus: String = "",
    @Expose @SerializedName("check_in_status") var checkInStatus: Int = -1,
    @Expose @SerializedName("jvs_details_uploaded") var jvsDetailsUploaded: String = "",
    @Expose @SerializedName("latitude") var latitude: String? = "",
    @Expose @SerializedName("longitude") var longitude: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(visitSchedulerId)
        parcel.writeString(industryIMISId)
        parcel.writeString(industryName)
        parcel.writeString(industryAddress)
        parcel.writeString(scheduledOn)
        parcel.writeString(visitedOn)
        parcel.writeString(reportSubmittedOn)
        parcel.writeString(complianceStatus)
        parcel.writeString(visitStatus)
        parcel.writeInt(checkInStatus)
        parcel.writeString(jvsDetailsUploaded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyVisitModel> {
        override fun createFromParcel(parcel: Parcel): MyVisitModel {
            return MyVisitModel(parcel)
        }

        override fun newArray(size: Int): Array<MyVisitModel?> {
            return arrayOfNulls(size)
        }
    }
}

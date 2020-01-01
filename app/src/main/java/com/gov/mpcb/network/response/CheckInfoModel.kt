package com.gov.mpcb.network.response

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CheckInfoModel(

    @Expose @SerializedName("latitude") var latitude: String? = "",
    @Expose @SerializedName("longitude") var longitude: String? = "",
    @Expose @SerializedName("selfie_img") var selfieImage: String? = "",
    @Expose @SerializedName("checked_in_date") var checkInDate: String? = ""

) : Parcelable
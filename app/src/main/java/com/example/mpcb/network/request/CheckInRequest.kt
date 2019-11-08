package com.example.mpcb.network.request

import com.google.gson.annotations.SerializedName

class CheckInRequest {

    @SerializedName("UserId")
    var userId: String = ""

    @SerializedName("visitId")
    var visitId: String = ""

    @SerializedName("latitude")
    var latitude: String = ""

    @SerializedName("longitude")
    var longitude: String = ""

}

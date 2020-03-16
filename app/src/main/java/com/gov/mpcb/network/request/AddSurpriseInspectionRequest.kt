package com.gov.mpcb.network.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddSurpriseInspectionRequest {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""

    @Expose
    @SerializedName("UserId")
    var userId = ""

    @Expose
    @SerializedName("industry_iin")
    var industryIin = ""

    @Expose
    @SerializedName("surprise_inspection_on")
    var surpriseInspectionOn = ""

    @Expose
    @SerializedName("reason_for_conducting_surprise_inspection")
    var reasonForConductingSurpriseInspection = ""
}
package com.gov.mpcb.network.response

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IdOtherDocumentDataResponse(
    @Expose
    @SerializedName("data") val data: JsonObject,
    @Expose
    @SerializedName("message") val message: String = "",
    @Expose
    @SerializedName("status") val status: Int = -1
)

data class IdOtherDocumentData(
    var title: String,
    var link: String
)
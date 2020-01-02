package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("isSuccessful") @Expose val isSuccessful: Boolean,
    @SerializedName("msgCode") @Expose val msgCode: String,
    @SerializedName("msgDesc") @Expose val msgDesc: String,
    @SerializedName("data") @Expose val data: T,
    @SerializedName("flag") @Expose val flag: Boolean,
    @SerializedName("resMsg") @Expose val resMsg: String
)
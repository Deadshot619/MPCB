package com.example.mpcb.network.response

import com.example.mpcb.network.request.Data
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ViewVisitResponse (
    @Expose @SerializedName("status") var status: Boolean,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("data") var data: Data
){

}
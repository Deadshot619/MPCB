package com.example.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @Expose @SerializedName("status") var status: String = "",
    @Expose @SerializedName("message") var message: String = ""
)

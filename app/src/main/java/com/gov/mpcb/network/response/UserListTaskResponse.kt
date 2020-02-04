package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserListTaskResponse(

    @Expose
    @SerializedName("name")
    var name: String = "",

    @Expose
    @SerializedName("value")
    var value: Int = -1
)
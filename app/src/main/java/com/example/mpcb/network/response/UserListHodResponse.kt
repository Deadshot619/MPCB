package com.example.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserListHodResponse(

    @Expose
    @SerializedName("user_id")
    var userId: Int = 0,

    @Expose
    @SerializedName("user_name")
    var userName: String = "",

    @Expose
    @SerializedName("designation")
    var designation: String = ""

    )
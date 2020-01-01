package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class UserListHodResponse(

    @Expose @SerializedName("status") var status: Int,
    @Expose @SerializedName("message") var message: String = "",
    @Expose @SerializedName("users") var users: List<Users>

)

data class Users (
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

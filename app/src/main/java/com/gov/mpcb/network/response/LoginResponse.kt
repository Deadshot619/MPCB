package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("name")
    @Expose
    var name: String? = null
        get() = field ?: ""
    @SerializedName("email")
    @Expose
    var email: String? = null
        get() = field ?: ""
    @SerializedName("tel_no")
    @Expose
    var mobile: String? = null
        get() = field ?: ""
    @SerializedName("designation")
    @Expose
    var designation: String? = null
        get() = field ?: ""
    @SerializedName("reporting_to")
    @Expose
    var reportingTo: String? = null
        get() = field ?: ""
    @SerializedName("status")
    @Expose
    var status: Int? = null
        get() = field ?: 0
    @SerializedName("UserId")
    @Expose
    var userId: Int? = null
        get() = field ?: 0
    @SerializedName("message")
    @Expose
    var message: String? = null
        get() = field ?: ""

    @SerializedName("has_subbordinate_officers")
    @Expose
    var hasSubbordinateOfficers: Int = 0

}

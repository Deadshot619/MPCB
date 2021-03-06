package com.gov.mpcb.network.request

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.mpcb.BR

class UpdateProfileRequest : BaseObservable() {

    @Expose
    @SerializedName("RequestId")
    var requestId = "123"

    @Expose
    @SerializedName("UserId")
    var userId = ""

    @Expose
    @SerializedName("name")
    var name = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @Expose
    @SerializedName("email_address")
    var email = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @Expose
    @SerializedName("tel_no")
    var phone = ""
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }
}

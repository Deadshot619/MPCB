package com.example.mpcb.network.request

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.mpcb.BR
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginRequest : BaseObservable() {

    @SerializedName("UserName")
    @Expose
    var userName = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.userName)
        }

    @SerializedName("Password")
    @Expose
    var password = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    @SerializedName("RequestId")
    @Expose
    var requestId = ""

    @SerializedName("DeviceToken")
    @Expose
    var deviceToken = ""
}

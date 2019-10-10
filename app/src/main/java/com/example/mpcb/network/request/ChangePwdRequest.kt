package com.example.mpcb.network.request

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.mpcb.BR
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChangePwdRequest : BaseObservable() {

    @Expose
    @SerializedName("RequestId")
    var requestId = ""
    @Expose
    @SerializedName("UserId")
    var userId = ""

    @Expose
    @SerializedName("current_password")
    var currentPwd = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentPwd)
        }
    @Expose
    @SerializedName("new_password")
    var newPwd = ""
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.newPwd)
        }
}

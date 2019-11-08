package com.example.mpcb.reports.bankgaurantee.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.mpcb.BR

data class BankGuaranteeDetailModel(
    val bgImposed: Boolean = false,
    val bgSubmitted: Boolean = false,
    val bgDate: String = ""
) : BaseObservable() {

    @get:Bindable
    var bgAgainst: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bgAgainst)
        }

    @get:Bindable
    var numberDirections: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.numberDirections)
        }

    @get:Bindable
    var bgImposedFor: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bgImposedFor)
        }

    @get:Bindable
    var bgNo: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bgNo)
        }

    @get:Bindable
    var bgValidity: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bgValidity)
        }
}
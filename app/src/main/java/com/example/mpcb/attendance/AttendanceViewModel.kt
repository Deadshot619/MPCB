package com.example.mpcb.attendance

import androidx.lifecycle.MutableLiveData
import com.example.mpcb.base.BaseViewModel
import com.example.mpcb.network.DataProvider
import com.example.mpcb.network.request.MyVisitRequest
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson
import io.reactivex.functions.Consumer

class AttendanceViewModel : BaseViewModel<AttendanceNavigator>() {
}
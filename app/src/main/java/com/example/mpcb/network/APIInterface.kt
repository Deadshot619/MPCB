package com.example.mpcb.network

import com.example.mpcb.network.request.ChangePwdRequest
import com.example.mpcb.network.request.DashboardDataRequest
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.network.request.UpdateProfileRequest
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.UpdateProfileResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {

    @POST("login")
    fun login(@Body loginReqModel: LoginRequest): Single<LoginResponse>

    @POST("update_profile")
    fun updateProfile(@Body request: UpdateProfileRequest): Single<UpdateProfileResponse>

    @POST("change_password")
    fun changePassword(@Body request: ChangePwdRequest): Single<UpdateProfileResponse>

    @POST("dashboard_data")
    fun getDashboardData(@Body request: DashboardDataRequest): Single<DashboardDataResponse>
}
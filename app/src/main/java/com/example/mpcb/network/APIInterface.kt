package com.example.mpcb.network

import com.example.mpcb.network.request.*
import com.example.mpcb.network.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIInterface {

    @POST("login")
    fun login(@Body loginReqModel: LoginRequest): Single<LoginResponse>

    @POST("update_profile")
    fun updateProfile(@Body request: UpdateProfileRequest): Single<UpdateProfileResponse>

    @POST("change_password")
    fun changePassword(@Body request: ChangePwdRequest): Single<UpdateProfileResponse>

    @POST("dashboard_data")
    fun getDashboardData(@Body request: DashboardDataRequest): Single<DashboardDataResponse>

    @POST("visit_list")
    fun getVisitList(@Body request: MyVisitRequest): Single<MyVisitResponse>

    @Multipart
    @POST("check_in")
    fun checkIn(
        @Part("UserId") userId: RequestBody,
        @Part("visitId") visitId: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part selfieImagePart: MultipartBody.Part
    ): Single<CheckInResponse>

    @POST("submit_visit_report")
    fun submitReport(@Body request: ReportRequest): Single<ReportSubmitResponse>
}
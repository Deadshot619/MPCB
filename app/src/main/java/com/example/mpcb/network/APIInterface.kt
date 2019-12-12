package com.example.mpcb.network

import com.example.mpcb.network.request.*
import com.example.mpcb.network.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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
        @Part("RequestId") requestId: RequestBody,
        @Part("UserId") userId: RequestBody,
        @Part("visitId") visitId: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part selfieImagePart: MultipartBody.Part
    ): Single<CheckInResponse>

    //View Check-in info
    @POST("view_check_in")
    fun getcheckInInfo(@Body request: MyVisitRequest) : Single<CheckInfoResponse>

    //Submit Visit Report data
    @POST("submit_visit_report")
    fun submitReport(@Body request: ReportRequest): Single<ReportSubmitResponse>

    //Upload Visit Report File
    @Multipart
    @POST("upload_visit_report_file")
    fun uploadVisitReportFile(
        @Part("RequestId") requestId: RequestBody,
        @Part("visitId") visitId: RequestBody,
        @Part("indus_imis_id") indusImisId: RequestBody,
        @Part("UserId") userId: RequestBody,
        @Part visitReportFile: MultipartBody.Part
    ): Single<ReportSubmitResponse>

    //View Visit Report data
    @POST("view_visit_report")
    fun viewVisitReport(@Body request: ViewVisitRequest): Single<ViewVisitResponse>

    //Get User List for Hods
    @POST("get_subordinate_officer")
    fun getUserListForHods(@Body request: UserListHodRequest): Single<UserListHodResponse>

    //Get User List Task
    @GET("get_user_list_task")
    fun getUserListTask(): Single<ArrayList<UserListTaskResponse>>


}
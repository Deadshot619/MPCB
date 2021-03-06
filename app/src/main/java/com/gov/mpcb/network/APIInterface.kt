package com.gov.mpcb.network

import com.google.gson.JsonElement
import com.gov.mpcb.network.request.*
import com.gov.mpcb.network.response.*
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

    //fetches uncompleted visit list
    @POST("view_uncompleted_visit_list")
    fun fetchUncompletedVisitList(@Body request: ViewUncompletedVisitRequest): Single<ViewUncompletedVisitResponse>

    //Submit Remark
    @POST("submit_uncompleted_visit_remark")
    fun submitUncompletedVisitRemark(@Body request: UncompletedVisitRemarkRequest): Single<CommonResponse>

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
    fun getcheckInInfo(@Body request: MyVisitRequest): Single<CheckInfoResponse>

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

    //Get Users Lists in Task Management
    @GET("get_user_list_task")
    fun getUserListTask(): Single<ArrayList<UserListTaskResponse>>

    //Check Application Version
    @POST("check_app_version")
    fun checkAppVersion(@Body request: AppVersionRequest): Single<AppVersionResponse>

    //View Task Details
    @POST("view_task_details")
    fun getTaskDetails(@Body request: TaskDetailsRequest): Single<TaskDetailsResponse>

    //Get Previous Visit Report Data
    //This api uses the same Request & Response model as View Visit Report
    @POST("view_prev_visit_report_data")
    fun getPreviousVisitReportData(@Body request: ViewVisitRequest): Single<ViewVisitResponse>

    /* Surprise Inspections APis*/
    //View Applied Lists
    @POST("view_applied_list")
    fun viewAppliedLists(@Body request: ViewAppliedListRequest): Single<ViewAppliedListResponse>

    //View available industry lists
    @POST("list_surprise_industries")
    fun getAvailableIndustryLists(@Body request: ViewAvailableIndustriesRequest): Single<ViewAvailableIndustriesResponse<ViewAvailableIndustriesData>>

    //Add Surprise Inspection
    @POST("add_surprise_inspection")
    fun addSurpriseInspection(@Body request: AddSurpriseInspectionRequest): Single<CommonResponse>

    //View Previous Inspection Lists
    @POST("view_previous_inspection_list")
    fun getPreviousConductedInspections(@Body request: ViewPreviousInspectionListRequest): Single<ViewPreviousInspectionListResponse>

    /*  Circulars APi   */
    //fetch Circulars data
    @GET("http://www.mpcb.gov.in/view/api/circulars")
    fun fetchCirculars(
        @Query("page") pageNo: Int,
        @Query("search") searchQuery: String
    ): Single<CircularsResponse>

    /*  Industry Directory APi   */
    //View Directory List data
    @POST("view_directory_list")
    fun fetchIndustryDirectoryList(@Body request: ViewDirectoryListRequest): Single<ViewAvailableIndustriesResponse<ViewDirectoryListData>>

    //View directory data
    @POST("view_directory_data")
    fun fetchApplicationDirectoryData(@Body request: ViewIndustryDirectoryDataRequest): Single<ViewIndustryDirectoryDataResponse<JsonElement>>

    //View Consent Document
    @POST("view_documents_consent")
    fun fetchConsentDocuments(@Body request: IdConsentDocumentRequest): Single<IdConsentDocumentResponse>

    //View Other Documents
    @POST("view_documents_other")
    fun fetchOtherDocuments(@Body request: IdOtherDocumentsRequest): Single<IdOtherDocumentDataResponse>

}
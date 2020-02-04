package com.gov.mpcb.network

import com.gov.mpcb.network.request.*
import com.gov.mpcb.network.response.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface RemoteDataProvider {

    fun login(
        request: LoginRequest,
        success: Consumer<LoginResponse>,
        error: Consumer<Throwable>
    ): Disposable

    fun updateProfile(
        request: UpdateProfileRequest,
        success: Consumer<UpdateProfileResponse>,
        error: Consumer<Throwable>
    ): Disposable

    fun changePassword(
        request: ChangePwdRequest,
        success: Consumer<UpdateProfileResponse>,
        error: Consumer<Throwable>
    ): Disposable

    fun getDashboardData(
        request: DashboardDataRequest,
        success: Consumer<DashboardDataResponse>,
        error: Consumer<Throwable>
    ): Disposable

    fun getVisitList(
        request: MyVisitRequest,
        success: Consumer<MyVisitResponse>,
        error: Consumer<Throwable>
    ): Disposable

    fun checkIn(
        requestId: RequestBody,
        userId: RequestBody,
        visitId: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        selfieImagePart: MultipartBody.Part,
        success: Consumer<CheckInResponse>,
        error: Consumer<Throwable>
    ):Disposable

    fun submitReport(
        request: ReportRequest,
        success: Consumer<ReportSubmitResponse>,
        error: Consumer<Throwable>
    ): Disposable


    fun  checkInInfo(request: MyVisitRequest,
                     success: Consumer<CheckInfoResponse>,
                     error: Consumer<Throwable>
    ):Disposable

    /**
     * Method to View Visit Report Data
     */
    fun viewVisitReport(
        request: ViewVisitRequest,
        success: Consumer<ViewVisitResponse>,
        error: Consumer<Throwable>
    ): Disposable

    /**
     * Method to Upload Visit Report File
     */
    fun uploadVisitReportFile(
        requestId: RequestBody,
        visitId: RequestBody,
        indusImisId: RequestBody,
        userId: RequestBody,
        visitReportFile: MultipartBody.Part,
        success: Consumer<ReportSubmitResponse>,
        error: Consumer<Throwable>
    ):Disposable

    /**
     * Method to get User List for Hods
     */
    fun getUserListDataForHods(
        request: UserListHodRequest,
        success: Consumer<UserListHodResponse>,
        error: Consumer<Throwable>
    ): Disposable

    /**
     * Method to get User List Task Data
     */
    fun getUserListData(
        success: Consumer<ArrayList<UserListTaskResponse>>,
        error: Consumer<Throwable>
    ):Disposable

    /**
     * Method to check current version of the App
     */
    fun checkCurrentVersion(
        request: AppVersionRequest,
        success: Consumer<AppVersionResponse>,
        error: Consumer<Throwable>
    ): Disposable

    /**
     * Method to get Task Details of a user
     */
    fun getTaskDetails(
        request: TaskDetailsRequest,
        success: Consumer<TaskDetailsResponse>,
        error: Consumer<Throwable>
    ): Disposable

    /**
     * Method to fetch Previous(common) Visit Report Data
     */
    fun fetchPreviousVisitReportData(
        request: ViewVisitRequest,
        success: Consumer<ViewVisitResponse>,
        error: Consumer<Throwable>
    ): Disposable
}
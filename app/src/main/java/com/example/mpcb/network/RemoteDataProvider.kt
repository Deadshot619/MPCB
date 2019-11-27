package com.example.mpcb.network

import com.example.mpcb.network.request.*
import com.example.mpcb.network.response.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import okhttp3.MultipartBody

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
        userId: String,
        visitId: String,
        latitude: String,
        longitude: String,
        selfieImagePart: MultipartBody.Part,
        success: Consumer<CheckInResponse>,
        error: Consumer<Throwable>
    ):Disposable

    fun submitReport(
        request: ReportRequest,
        success: Consumer<ReportSubmitResponse>,
        error: Consumer<Throwable>
    ): Disposable

}
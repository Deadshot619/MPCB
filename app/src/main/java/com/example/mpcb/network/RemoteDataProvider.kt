package com.example.mpcb.network

import com.example.mpcb.network.request.ChangePwdRequest
import com.example.mpcb.network.request.DashboardDataRequest
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.network.request.UpdateProfileRequest
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.UpdateProfileResponse
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

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

}
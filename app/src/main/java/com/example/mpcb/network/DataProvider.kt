package com.example.mpcb.network

import com.example.mpcb.network.request.ChangePwdRequest
import com.example.mpcb.network.request.DashboardDataRequest
import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.network.request.UpdateProfileRequest
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.UpdateProfileResponse
import com.example.mpcb.utils.isNetworkAvailable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

object DataProvider : RemoteDataProvider {

    private val mServices: APIInterface by lazy { APIClient.getClient().create(APIInterface::class.java) }

    private fun noInternetAvailable(error: Consumer<Throwable>) =
        error.accept(Throwable("No Internet Connection"))

    private fun getDefaultDisposable(): Disposable = object : Disposable {
        override fun isDisposed() = false
        override fun dispose() {}
    }

    override fun login(
        request: LoginRequest, success: Consumer<LoginResponse>, error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.login(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status != 1) {
                    error.accept(Throwable(response.message))
                } else {
                    success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    override fun updateProfile(
        request: UpdateProfileRequest,
        success: Consumer<UpdateProfileResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.updateProfile(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status.equals("0")) {
                    error.accept(Throwable(response.message))
                } else {
                    success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    override fun changePassword(
        request: ChangePwdRequest,
        success: Consumer<UpdateProfileResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.changePassword(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status.equals("0")) {
                    error.accept(Throwable(response.message))
                } else {
                    success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    override fun getDashboardData(
        request: DashboardDataRequest,
        success: Consumer<DashboardDataResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getDashboardData(request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status.equals("0")) {
                    error.accept(Throwable(response.message))
                } else {
                    success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

}
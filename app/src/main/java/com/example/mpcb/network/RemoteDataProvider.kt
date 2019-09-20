package com.example.mpcb.network

import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.network.response.LoginResponse
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface RemoteDataProvider {

    fun login(
        request: LoginRequest,
        success: Consumer<LoginResponse>,
        error: Consumer<Throwable>
    ): Disposable

}
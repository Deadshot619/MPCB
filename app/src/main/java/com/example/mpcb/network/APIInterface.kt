package com.example.mpcb.network

import com.example.mpcb.network.request.LoginRequest
import com.example.mpcb.network.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {

    @POST("login")
    fun login(@Body loginReqModel: LoginRequest): Single<LoginResponse>
}
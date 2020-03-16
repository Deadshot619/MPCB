package com.gov.mpcb.network

import com.gov.mpcb.network.request.*
import com.gov.mpcb.network.response.*
import com.gov.mpcb.utils.isNetworkAvailable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

object DataProvider : RemoteDataProvider {

    private val mServices: APIInterface by lazy {
        APIClient.getClient().create(APIInterface::class.java)
    }

    private fun noInternetAvailable(error: Consumer<Throwable>) =
        error.accept(Throwable("No Internet Connection"))

    private fun getDefaultDisposable(): Disposable = object : Disposable {
        override fun isDisposed() = false
        override fun dispose() {}
    }

    override fun login(
        request: LoginRequest, success: Consumer<LoginResponse>, error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.login(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        mServices.updateProfile(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        mServices.changePassword(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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
        mServices.getDashboardData(request).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status == 0) {
                    error.accept(Throwable(response.message))
                } else {
                    success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    override fun getVisitList(
        request: MyVisitRequest,
        success: Consumer<MyVisitResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getVisitList(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status == "0") {
                    error.accept(Throwable(response.message))
                } else {
                    success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }


    override fun checkInInfo(
        request: MyVisitRequest,
        success: Consumer<CheckInfoResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getcheckInInfo(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { response ->
                if (response.status) {
                    success.accept(response)
                    // error.accept(Throwable(response.message))
                } else {
                    error.accept(Throwable(response.message))
                    //success.accept(response)
                }
            }, error)
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    override fun checkIn(
        requestId: RequestBody,
        userId: RequestBody,
        visitId: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        selfieImagePart: MultipartBody.Part,
        success: Consumer<CheckInResponse>,
        error: Consumer<Throwable>
    ): Disposable =
        if (isNetworkAvailable()) {
            mServices.checkIn(
                requestId,
                userId,
                visitId,
                latitude,
                longitude,
                selfieImagePart
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer(fun(response: CheckInResponse) {
                    if (!response.status) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                }), error)
        } else {
            noInternetAvailable(error)
            getDefaultDisposable()
        }


    override fun submitReport(
        request: ReportRequest,
        success: Consumer<ReportSubmitResponse>,
        error: Consumer<Throwable>
    ): Disposable =
        if (isNetworkAvailable()) {
            mServices.submitReport(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    Consumer { response ->
                        if (!response.status) {
                            error.accept(Throwable(response.message))
                        } else {
                            success.accept(response)
                        }
                    },
                    error
                )
        } else {
            noInternetAvailable(error)
            getDefaultDisposable()
        }

    override fun viewVisitReport(
        request: ViewVisitRequest,
        success: Consumer<ViewVisitResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.viewVisitReport(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (!response.status) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to Upload Visit Report File
     */
    override fun uploadVisitReportFile(
        requestId: RequestBody,
        visitId: RequestBody,
        indusImisId: RequestBody,
        userId: RequestBody,
        visitReportFile: MultipartBody.Part,
        success: Consumer<ReportSubmitResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.uploadVisitReportFile(
            requestId = requestId,
            visitId = visitId,
            indusImisId = indusImisId,
            userId = userId,
            visitReportFile = visitReportFile
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (!response.status) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to get User List for Hods
     */
    override fun getUserListDataForHods(
        request: UserListHodRequest,
        success: Consumer<UserListHodResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getUserListForHods(request = request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (response.status != 1) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )

    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to get User List Task Data
     */
    override fun getUserListTaskData(
        success: Consumer<ArrayList<UserListTaskResponse>>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getUserListTask()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                    success.accept(it)
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to check current version of the App
     */
    override fun checkCurrentVersion(
        request: AppVersionRequest,
        success: Consumer<AppVersionResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.checkAppVersion(request = request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                    success.accept(it)
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }


    /**
     * Method to get Task Details of a user
     */
    override fun getTaskDetails(
        request: TaskDetailsRequest,
        success: Consumer<TaskDetailsResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getTaskDetails(request = request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                    success.accept(it)
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to fetch Previous(common) Visit Report Data
     */
    override fun fetchPreviousVisitReportData(
        request: ViewVisitRequest,
        success: Consumer<ViewVisitResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getPreviousVisitReportData(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (!response.status) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to fetch Surprise Inspections List applied by user
     */
    override fun getAppliedLists(
        request: ViewAppliedListRequest,
        success: Consumer<ViewAppliedListResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.viewAppliedLists(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (response.status != 1) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }


    /**
     * Method to fetch Industry List which are available for Surprise Inspections
     */
    override fun getAvailableIndustries(
        request: ViewAvailableIndustriesRequest,
        success: Consumer<ViewAvailableIndustriesResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
            mServices.getAvailableIndustryLists(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    Consumer { response ->
                        if (response.status != 1) {
                            error.accept(Throwable(response.message))
                        } else {
                            success.accept(response)
                        }
                    },
                    error
                )
        } else {
            noInternetAvailable(error)
            getDefaultDisposable()
        }

    /**
     * Method to submit request for Surprise Inspection
     */
    override fun addSurpriseInspections(
        request: AddSurpriseInspectionRequest,
        success: Consumer<AddSurpriseInspectionResponse>,
        error: Consumer<Throwable>
    ):Disposable = if (isNetworkAvailable()) {
        mServices.addSurpriseInspection(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (response.status != 1) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

    /**
     * Method to get Previously Conducted Inspections data
     */
    override fun getPreviousConductedInspections(
        request: ViewPreviousInspectionListRequest,
        success: Consumer<ViewPreviousInspectionListResponse>,
        error: Consumer<Throwable>
    ): Disposable = if (isNetworkAvailable()) {
        mServices.getPreviousConductedInspections(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer { response ->
                    if (response.status != 1) {
                        error.accept(Throwable(response.message))
                    } else {
                        success.accept(response)
                    }
                },
                error
            )
    } else {
        noInternetAvailable(error)
        getDefaultDisposable()
    }

}
package com.gov.mpcb.menu_tabs.industry_directory.documents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.network.DataProvider
import com.gov.mpcb.network.request.IdConsentDocumentRequest
import com.gov.mpcb.network.request.IdOtherDocumentsRequest
import com.gov.mpcb.network.response.IdConsentDocumentsData
import com.gov.mpcb.utils.LoadingStatus
import io.reactivex.functions.Consumer

class IdDocumentsViewModel : BaseViewModel<IdDocumentsNavigator>() {

    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

    //Variable to hold [IdConsentDocumentsData] data
    private val idConsentDocumentsData = MutableLiveData<List<IdConsentDocumentsData>>()
    val _idConsentDocumentsData: LiveData<List<IdConsentDocumentsData>>
        get() = idConsentDocumentsData

    /**
     * This method will call api for Consent Document Data
     */
    fun getConsentDocumentsData(applicantId: Int) {
        val request = IdConsentDocumentRequest().apply {
            this.applicantId = applicantId
        }


        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getConsentDocuments(
                request = request,
                success = Consumer {
                    idConsentDocumentsData.value = it.data
                    progressStatus.value = LoadingStatus.DONE
                },
                error = Consumer {
                    checkError(it)
                    progressStatus.value = LoadingStatus.ERROR
                }
            )
        )
    }

    /**
     * This method will call api for Other Document Data
     */
    fun getOtherDocumentsData(applicantId: Int, type: String) {
        val request = IdOtherDocumentsRequest().apply {
            this.applicantId = applicantId
            this.type = type
        }


        progressStatus.value = LoadingStatus.LOADING

        mDisposable.add(
            DataProvider.getOtherDocuments(
                request = request,
                success = Consumer {
                    idConsentDocumentsData.value = convertJsonObjectToList(it.data)
                    progressStatus.value = LoadingStatus.DONE
                },
                error = Consumer {
                    checkError(it)
                    progressStatus.value = LoadingStatus.ERROR
                }
            )
        )
    }

    /**
     * This method will convert a [JsonObject] to list of [IdConsentDocumentsData]
     */
    private fun convertJsonObjectToList(jsonObject: JsonObject): List<IdConsentDocumentsData> {
        val keys: MutableSet<String> = jsonObject.keySet()
        val list: MutableList<IdConsentDocumentsData> = mutableListOf()

        for ((id, key) in keys.withIndex()) {
            list.add(
                IdConsentDocumentsData(
                    Id = id + 1,
                    Document_name = key,
                    view_link = jsonObject[key].toString()
                )
            )
        }

        return list
    }


}

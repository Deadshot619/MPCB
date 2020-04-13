package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IdConsentDocumentResponse(
    @Expose
    @SerializedName("data") val data: List<IdConsentDocumentsData> = listOf(),
    @Expose
    @SerializedName("message") val message: String = "",
    @Expose
    @SerializedName("status") val status: Int = -1
)

data class IdConsentDocumentsData(
    @Expose @SerializedName("Application_id") val Application_id: Int = 0,
    @Expose @SerializedName("Created") val Created: String = "",
    @Expose @SerializedName("Document_file") val Document_file: String = "",
    @Expose @SerializedName("Document_location") val Document_location: String = "",
    @Expose @SerializedName("Document_name") val Document_name: String = "",
    @Expose @SerializedName("Document_type") val Document_type: String = "",
    @Expose @SerializedName("Id") val Id: Int = 0,
    @Expose @SerializedName("category_name") val category_name: String = "",
    @Expose @SerializedName("view_link") val view_link: String = ""
)
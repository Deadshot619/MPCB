package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CircularsResponse (
    @Expose @SerializedName("JSONArray") val data : List<CircularsData>?
)

data class CircularsData(
    @Expose @SerializedName("Date") val date: String,
    @Expose @SerializedName("Department") val department: String,
    @Expose @SerializedName("PdfLink") val pdfLink: String,
    @Expose @SerializedName("Title") val title: String
)
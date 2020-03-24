package com.gov.mpcb.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CircularsResponse (
    @Expose @SerializedName("results") val data : List<CircularsData>?,
    @Expose @SerializedName("pager") val pager : Pager
)

data class CircularsData(
    @Expose @SerializedName("Date") val date: String,
    @Expose @SerializedName("Department") val department: String,
    @Expose @SerializedName("PdfLink") val pdfLink: String,
    @Expose @SerializedName("Title") val title: String
)

data class Pager(
    @Expose @SerializedName("current_page") val current_page: Int,
    @Expose @SerializedName("items_per_page") val items_per_page: Int,
    @Expose @SerializedName("next_page") val next_page: Int,
    @Expose @SerializedName("pages") val pages: Int,
    @Expose @SerializedName("total_row_count") val total_row_count: String
)
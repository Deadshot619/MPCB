package com.gov.mpcb.utils.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.menu_tabs.industry_directory.authorization.IdAuthorizationAdapter
import com.gov.mpcb.menu_tabs.industry_directory.bank_guarantee.IdBankGuaranteeAdapter
import com.gov.mpcb.menu_tabs.industry_directory.id_industry_list.IdConsentAdapter
import com.gov.mpcb.menu_tabs.industry_directory.id_industry_list.IdIndustryListAdapter
import com.gov.mpcb.menu_tabs.industry_directory.legal.IdLegalAdapter
import com.gov.mpcb.menu_tabs.industry_directory.submission.IdSubmissionAdapter
import com.gov.mpcb.menu_tabs.industry_directory.visits.IdVisitsAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection.PreviouslyConductedInspectionAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.IndustryListAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.ShowCircularsAdapter
import com.gov.mpcb.network.response.*
import com.gov.mpcb.utils.LoadingStatus

/**
 * This adapter will hide the recycler while the recycler view is loading or when some error occured
 */
@BindingAdapter("recyclerViewLoading")
fun bindRecyclerViewLoading(recyclerView: RecyclerView, status: LoadingStatus?) {
    when (status) {
        LoadingStatus.LOADING -> {
            recyclerView.visibility = View.INVISIBLE
        }
        LoadingStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
        LoadingStatus.ERROR -> {
            recyclerView.visibility = View.INVISIBLE
        }
    }
}

/**
 * When there is no Products List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listAvailableIndustryData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ViewAvailableIndustriesData>?){
    val adapter = recyclerView.adapter as IndustryListAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listPreviouslyConductedInspections")
fun bindRVPreviouslyConductedInspections(recyclerView: RecyclerView, data: List<ViewPreviousInspectionListData>?){
    val adapter = recyclerView.adapter as PreviouslyConductedInspectionAdapter
    adapter.submitList(data)
}

//CIRCULARS
/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listCircularsData")
fun bindListCircularsData(recyclerView: RecyclerView, data: List<CircularsData>?){
    val adapter = recyclerView.adapter as ShowCircularsAdapter
    adapter.submitList(data)
}

//INDUSTRY DIRECTORY
/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listIndustryDirectory")
fun bindListIndustryDirectory(recyclerView: RecyclerView, data: List<ViewDirectoryListData>?){
    val adapter = recyclerView.adapter as IdIndustryListAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listConsent")
fun bindListConsent(recyclerView: RecyclerView, data: List<IdConsentData>?){
    val adapter = recyclerView.adapter as IdConsentAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listAuthorization")
fun bindListAuthorization(recyclerView: RecyclerView, data: List<IdAuthorizationData>?){
    val adapter = recyclerView.adapter as IdAuthorizationAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listSubmission")
fun bindListSubmission(recyclerView: RecyclerView, data: List<IdSubmissionData>?){
    val adapter = recyclerView.adapter as IdSubmissionAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listBG")
fun bindListBg(recyclerView: RecyclerView, data: List<IdBankGuaranteeData>?){
    val adapter = recyclerView.adapter as IdBankGuaranteeAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listVisits")
fun bindListVisits(recyclerView: RecyclerView, data: List<IdVisitData>?){
    val adapter = recyclerView.adapter as IdVisitsAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listLegal")
fun bindListLegal(recyclerView: RecyclerView, data: List<IdLegalData>?){
    val adapter = recyclerView.adapter as IdLegalAdapter
    adapter.submitList(data)
}
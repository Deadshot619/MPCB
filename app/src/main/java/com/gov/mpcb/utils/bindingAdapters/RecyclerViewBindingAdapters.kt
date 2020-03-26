package com.gov.mpcb.utils.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.menu_tabs.industry_directory.id_industry_list.IdIndustryListAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection.PreviouslyConductedInspectionAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.IndustryListAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.ShowCircularsAdapter
import com.gov.mpcb.network.response.CircularsData
import com.gov.mpcb.network.response.ViewAvailableIndustriesData
import com.gov.mpcb.network.response.ViewDirectoryListData
import com.gov.mpcb.network.response.ViewPreviousInspectionListData
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

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listCircularsData")
fun bindListCircularsData(recyclerView: RecyclerView, data: List<CircularsData>?){
    val adapter = recyclerView.adapter as ShowCircularsAdapter
    adapter.submitList(data)
}

/**
 * When there is no List data (data is null), hide the [RecyclerView],
 * otherwise show it.
 */
@BindingAdapter("listIndustryDirectory")
fun bindListIndustryDirectory(recyclerView: RecyclerView, data: List<ViewDirectoryListData>?){
    val adapter = recyclerView.adapter as IdIndustryListAdapter
    adapter.submitList(data)
}
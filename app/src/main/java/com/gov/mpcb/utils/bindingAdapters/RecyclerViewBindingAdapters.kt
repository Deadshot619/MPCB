package com.gov.mpcb.utils.bindingAdapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection.PreviouslyConductedInspectionAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.IndustryListAdapter
import com.gov.mpcb.network.response.ViewAvailableIndustriesData
import com.gov.mpcb.network.response.ViewPreviousInspectionListData

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
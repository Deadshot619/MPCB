package com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemPreviouslyConductedInspectionBinding
import com.gov.mpcb.network.response.ViewPreviousInspectionListData

class PreviouslyConductedInspectionAdapter :
    ListAdapter<ViewPreviousInspectionListData, PreviouslyConductedInspectionAdapter.PreviouslyConductedInspectionViewHolder>(
        DiffCallback
    ) {

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ViewPreviousInspectionListData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ViewPreviousInspectionListData>() {
        override fun areItemsTheSame(
            oldItem: ViewPreviousInspectionListData,
            newItem: ViewPreviousInspectionListData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewPreviousInspectionListData,
            newItem: ViewPreviousInspectionListData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviouslyConductedInspectionViewHolder {
        return PreviouslyConductedInspectionViewHolder(
            ItemPreviouslyConductedInspectionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PreviouslyConductedInspectionViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1)
    }


    /**
     * The [PreviouslyConductedInspectionViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [ViewPreviousInspectionListData] information.
     */
    class PreviouslyConductedInspectionViewHolder(private var binding: ItemPreviouslyConductedInspectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ViewPreviousInspectionListData?, count: Int) {
            binding.run {
                data = item
                tvNumber.text = "$count"
                executePendingBindings()
            }
        }


    }
}
package com.gov.mpcb.menu_tabs.surprise_inspections.industry_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIndustryListBinding
import com.gov.mpcb.network.response.ViewAvailableIndustriesData

class IndustryListAdapter(val listener: OnClickListener) :
    ListAdapter<ViewAvailableIndustriesData, IndustryListAdapter.IndustryListViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ViewAvailableIndustriesData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ViewAvailableIndustriesData>() {
        override fun areItemsTheSame(
            oldItem: ViewAvailableIndustriesData,
            newItem: ViewAvailableIndustriesData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewAvailableIndustriesData,
            newItem: ViewAvailableIndustriesData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryListViewHolder {
        return IndustryListViewHolder(
            ItemIndustryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IndustryListViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1, listener)
    }


    /**
     * The [IndustryListViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [ViewAvailableIndustriesData] information.
     */
    class IndustryListViewHolder(private var binding: ItemIndustryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ViewAvailableIndustriesData?,
            count: Int,
            listener: OnClickListener
        ) {
            binding.run {
                data = item
                clickListener = listener
                tvNumber.text = "$count"
                executePendingBindings()
            }
        }

    }


    /**
     * Interface to call in the [OnClickListener] & passed on to fragment to implement
     */
    class OnClickListener(val clickListener: (viewAvailableIndustriesData: ViewAvailableIndustriesData) -> Unit) {
        fun onClick(viewAvailableIndustriesData: ViewAvailableIndustriesData) =
            clickListener(viewAvailableIndustriesData)
    }

}
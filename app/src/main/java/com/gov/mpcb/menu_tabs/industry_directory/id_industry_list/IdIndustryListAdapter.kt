package com.gov.mpcb.menu_tabs.industry_directory.id_industry_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdIndustryListBinding
import com.gov.mpcb.network.response.ViewDirectoryListData

class IdIndustryListAdapter(val listener: OnClickListener) :
    ListAdapter<ViewDirectoryListData, IdIndustryListAdapter.IdIndustryListViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ViewDirectoryListData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ViewDirectoryListData>() {
        override fun areItemsTheSame(
            oldItem: ViewDirectoryListData,
            newItem: ViewDirectoryListData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewDirectoryListData,
            newItem: ViewDirectoryListData
        ): Boolean {
            //TODO 26/03/2020 : Ask sayan for unique id
            return oldItem.industryId == newItem.industryId
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdIndustryListViewHolder {
        return IdIndustryListViewHolder(
            ItemIdIndustryListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdIndustryListViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1, listener)
    }


    /**
     * The [IdIndustryListViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [ViewDirectoryListData] information.
     */
    class IdIndustryListViewHolder(private var binding: ItemIdIndustryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ViewDirectoryListData?,
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
    class OnClickListener(val clickListener: (viewAvailableIndustriesData: ViewDirectoryListData) -> Unit) {
        fun onClick(viewAvailableIndustriesData: ViewDirectoryListData) =
            clickListener(viewAvailableIndustriesData)
    }

}
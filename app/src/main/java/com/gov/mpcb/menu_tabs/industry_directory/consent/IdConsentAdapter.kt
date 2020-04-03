package com.gov.mpcb.menu_tabs.industry_directory.id_industry_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdConsentBinding
import com.gov.mpcb.network.response.IdConsentData

class IdConsentAdapter(val listener: OnClickListener) :
    ListAdapter<IdConsentData, IdConsentAdapter.IdConsentViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdConsentData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdConsentData>() {
        override fun areItemsTheSame(
            oldItem: IdConsentData,
            newItem: IdConsentData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdConsentData,
            newItem: IdConsentData
        ): Boolean {
            //TODO 26/03/2020 : Ask sayan for unique id
            return oldItem.unique_id == newItem.unique_id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdConsentViewHolder {
        return IdConsentViewHolder(
            ItemIdConsentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdConsentViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1, listener)
    }


    /**
     * The [IdConsentViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdConsentData] information.
     */
    class IdConsentViewHolder(private var binding: ItemIdConsentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdConsentData?,
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
    interface OnClickListener {
        fun onEyeClick(idConsentData: IdConsentData)
        fun onReportClick(idConsentData: IdConsentData)
    }

}
package com.gov.mpcb.menu_tabs.industry_directory.bank_guarantee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdBankGuaranteeBinding
import com.gov.mpcb.network.response.IdBankGuaranteeData

class IdBankGuaranteeAdapter:
    ListAdapter<IdBankGuaranteeData, IdBankGuaranteeAdapter.IdBankGuaranteeViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdBankGuaranteeData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdBankGuaranteeData>() {
        override fun areItemsTheSame(
            oldItem: IdBankGuaranteeData,
            newItem: IdBankGuaranteeData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdBankGuaranteeData,
            newItem: IdBankGuaranteeData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdBankGuaranteeViewHolder {
        return IdBankGuaranteeViewHolder(
            ItemIdBankGuaranteeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdBankGuaranteeViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1 /*listener*/)
    }


    /**
     * The [IdBankGuaranteeViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdBankGuaranteeData] information.
     */
    class IdBankGuaranteeViewHolder(private var binding: ItemIdBankGuaranteeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdBankGuaranteeData?,
            count: Int
//            listener: OnClickListener
        ) {
            binding.run {
                data = item
//                clickListener = listener
                tvNumber.text = "$count"
                executePendingBindings()
            }
        }

    }


    /**
     * Interface to call in the [OnClickListener] & passed on to fragment to implement
     */
    /*class OnClickListener(val clickListener: (viewAvailableIndustriesData: IdBankGuaranteeData) -> Unit) {
        fun onClick(viewAvailableIndustriesData: IdBankGuaranteeData) =
            clickListener(viewAvailableIndustriesData)
    }*/

}
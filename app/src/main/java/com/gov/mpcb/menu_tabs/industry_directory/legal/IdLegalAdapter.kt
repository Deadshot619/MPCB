package com.gov.mpcb.menu_tabs.industry_directory.legal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdLegalBinding
import com.gov.mpcb.network.response.IdLegalData

class IdLegalAdapter:
    ListAdapter<IdLegalData, IdLegalAdapter.IdLegalViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdLegalData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdLegalData>() {
        override fun areItemsTheSame(
            oldItem: IdLegalData,
            newItem: IdLegalData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdLegalData,
            newItem: IdLegalData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdLegalViewHolder {
        return IdLegalViewHolder(
            ItemIdLegalBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdLegalViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1 /*listener*/)
    }


    /**
     * The [IdLegalViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdLegalData] information.
     */
    class IdLegalViewHolder(private var binding: ItemIdLegalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdLegalData?,
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
    /*class OnClickListener(val clickListener: (viewAvailableIndustriesData: IdLegalData) -> Unit) {
        fun onClick(viewAvailableIndustriesData: IdLegalData) =
            clickListener(viewAvailableIndustriesData)
    }*/

}

package com.gov.mpcb.menu_tabs.industry_directory.authorization

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdAuthorizationBinding
import com.gov.mpcb.network.response.IdAuthorizationData

class IdAuthorizationAdapter(val listener: OnClickListener) :
ListAdapter<IdAuthorizationData, IdAuthorizationAdapter.IdAuthorizationViewHolder>(
DiffCallback
) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdAuthorizationData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdAuthorizationData>() {
        override fun areItemsTheSame(
            oldItem: IdAuthorizationData,
            newItem: IdAuthorizationData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdAuthorizationData,
            newItem: IdAuthorizationData
        ): Boolean {
            //TODO 26/03/2020 : Ask sayan for unique id
            return oldItem.unique_id == newItem.unique_id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdAuthorizationViewHolder {
        return IdAuthorizationViewHolder(
            ItemIdAuthorizationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdAuthorizationViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1 ,listener)
    }


    /**
     * The [IdAuthorizationViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdAuthorizationData] information.
     */
    class IdAuthorizationViewHolder(private var binding: ItemIdAuthorizationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdAuthorizationData?,
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
        fun onEyeClick(idAuthData: IdAuthorizationData)
        fun onReportClick(idAuthData: IdAuthorizationData)
    }

}
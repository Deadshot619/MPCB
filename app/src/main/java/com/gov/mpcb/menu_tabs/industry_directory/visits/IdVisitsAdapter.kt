package com.gov.mpcb.menu_tabs.industry_directory.visits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdVisitsBinding
import com.gov.mpcb.network.response.IdVisitData

class IdVisitsAdapter:
    ListAdapter<IdVisitData, IdVisitsAdapter.IdVisitsViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdVisitData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdVisitData>() {
        override fun areItemsTheSame(
            oldItem: IdVisitData,
            newItem: IdVisitData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdVisitData,
            newItem: IdVisitData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdVisitsViewHolder {
        return IdVisitsViewHolder(
            ItemIdVisitsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdVisitsViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1 /*listener*/)
    }


    /**
     * The [IdVisitsViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdVisitData] information.
     */
    class IdVisitsViewHolder(private var binding: ItemIdVisitsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdVisitData?,
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
    /*class OnClickListener(val clickListener: (viewAvailableIndustriesData: IdVisitData) -> Unit) {
        fun onClick(viewAvailableIndustriesData: IdVisitData) =
            clickListener(viewAvailableIndustriesData)
    }*/

}
package com.gov.mpcb.menu_tabs.industry_directory.submission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdSubmissionBinding
import com.gov.mpcb.network.response.IdSubmissionData

class IdSubmissionAdapter(val listener: OnClickListener):
    ListAdapter<IdSubmissionData, IdSubmissionAdapter.IdSubmissionViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdSubmissionData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdSubmissionData>() {
        override fun areItemsTheSame(
            oldItem: IdSubmissionData,
            newItem: IdSubmissionData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdSubmissionData,
            newItem: IdSubmissionData
        ): Boolean {
            //TODO 26/03/2020 : Ask sayan for unique id
            return oldItem.unique_id == newItem.unique_id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdSubmissionViewHolder {
        return IdSubmissionViewHolder(
            ItemIdSubmissionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdSubmissionViewHolder, position: Int) {
        holder.bind(getItem(position), position + 1 ,listener)
    }


    /**
     * The [IdSubmissionViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdSubmissionData] information.
     */
    class IdSubmissionViewHolder(private var binding: ItemIdSubmissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdSubmissionData?,
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
        fun onEyeClick(idSubmissionData: IdSubmissionData)
        fun onReportClick()
    }

}
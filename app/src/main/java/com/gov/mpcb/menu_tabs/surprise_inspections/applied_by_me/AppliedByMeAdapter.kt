package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemAppliedByMeBinding
import com.gov.mpcb.network.response.ViewAppliedListData

class AppliedByMeAdapter(val listener: OnClickListener) :
    ListAdapter<ViewAppliedListData, AppliedByMeAdapter.AppliedByMeViewHolder>(DiffCallback) {

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ViewAppliedListData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ViewAppliedListData>() {
        override fun areItemsTheSame(
            oldItem: ViewAppliedListData,
            newItem: ViewAppliedListData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ViewAppliedListData,
            newItem: ViewAppliedListData
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AppliedByMeViewHolder {
        return AppliedByMeViewHolder(
            ItemAppliedByMeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AppliedByMeAdapter.AppliedByMeViewHolder, position: Int) {
        val viewAppliedListData = getItem(position)
        holder.bind(viewAppliedListData, position+1, listener)
    }

    /**
     * The [AppliedByMeViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [ViewAppliedListData] information.
     */

    class AppliedByMeViewHolder(private var binding: ItemAppliedByMeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            viewAppliedListData: ViewAppliedListData,
            counter: Int,
            listener: OnClickListener
        ) {
            binding.run {
                data = viewAppliedListData
                clickListener = listener
                tvNumber.text = "$counter"
                executePendingBindings()
            }
        }
    }

    /**
     * Interface to call in the [OnClickListener] & passed on to fragment to implement
     */
    class OnClickListener(val clickListener: (viewAppliedListData: ViewAppliedListData) -> Unit) {
        fun onClick(viewAppliedListData: ViewAppliedListData) = clickListener(viewAppliedListData)
    }

}
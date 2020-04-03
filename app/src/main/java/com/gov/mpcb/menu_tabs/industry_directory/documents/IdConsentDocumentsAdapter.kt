package com.gov.mpcb.menu_tabs.industry_directory.documents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemIdDocumentsBinding
import com.gov.mpcb.network.response.IdConsentDocumentsData

class IdConsentDocumentsAdapter(val listener: OnClickListener) :
    ListAdapter<IdConsentDocumentsData, IdConsentDocumentsAdapter.IdConsentDocumentsViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [IdConsentDocumentsData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<IdConsentDocumentsData>() {
        override fun areItemsTheSame(
            oldItem: IdConsentDocumentsData,
            newItem: IdConsentDocumentsData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: IdConsentDocumentsData,
            newItem: IdConsentDocumentsData
        ): Boolean {
            return oldItem.Id == newItem.Id
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IdConsentDocumentsViewHolder {
        return IdConsentDocumentsViewHolder(
            ItemIdDocumentsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: IdConsentDocumentsViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }


    /**
     * The [IdConsentDocumentsViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [IdConsentDocumentsData] information.
     */
    class IdConsentDocumentsViewHolder(private var binding: ItemIdDocumentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: IdConsentDocumentsData?,
            listener: OnClickListener
        ) {
            binding.run {
                data = item
                clickListener = listener
                executePendingBindings()
            }
        }

    }


    /**
     * Interface to call in the [OnClickListener] & passed on to fragment to implement
     */
    class OnClickListener(val clickListener: (idConsentDocumentsData: IdConsentDocumentsData) -> Unit) {
        fun onClick(idConsentDocumentsData: IdConsentDocumentsData) =
            clickListener(idConsentDocumentsData)
    }

}

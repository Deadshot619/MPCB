package com.gov.mpcb.menu_tabs.surprise_inspections.industry_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemCircularsBinding
import com.gov.mpcb.network.response.CircularsData

class ShowCircularsAdapter(val listener: OnClickListener) :
    ListAdapter<CircularsData, ShowCircularsAdapter.ShowCircularsViewHolder>(
        DiffCallback
    ) {
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [CircularsData]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<CircularsData>() {
        override fun areItemsTheSame(
            oldItem: CircularsData,
            newItem: CircularsData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: CircularsData,
            newItem: CircularsData
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowCircularsViewHolder {
        return ShowCircularsViewHolder(
            ItemCircularsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ShowCircularsViewHolder, position: Int) {
        holder.bind(getItem(position),  listener)
    }


    /**
     * The [ShowCircularsViewHolder] constructor takes the binding variable from the associated
     * layout, which nicely gives it access to the full [CircularsData] information.
     */
    class ShowCircularsViewHolder(private var binding: ItemCircularsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: CircularsData?,
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
    class OnClickListener(val clickListener: (circularsData: CircularsData) -> Unit) {
        fun onClick(circularsData: CircularsData) =
            clickListener(circularsData)
    }

}
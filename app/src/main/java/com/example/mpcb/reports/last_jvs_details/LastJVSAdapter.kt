package com.example.mpcb.reports.last_jvs_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemLastJvsBinding
import com.example.mpcb.network.request.JvsSampleCollectedWaterSource
import com.example.mpcb.network.request.LastJVSChild


class LastJVSAdapter(
    val context: Context,
    private val viewModel: LastJVSViewModel
) : RecyclerView.Adapter<LastJVSAdapter.LastJvsViewHolder>() {

    private val parentList = ArrayList<JvsSampleCollectedWaterSource>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastJvsViewHolder {
        val itemBinding = ItemLastJvsBinding.inflate(mInflater, parent, false)
        return LastJvsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LastJvsViewHolder, position: Int) {
        val item = parentList[position]
        holder.setData(viewModel, item.lastJvsChild, position)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = parentList.size

    fun updateList(list: ArrayList<JvsSampleCollectedWaterSource>) {
        this.parentList.clear()
        this.parentList.addAll(list)
        notifyDataSetChanged()
    }

    class LastJvsViewHolder(val itemBinding: ItemLastJvsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(
            viewModel: LastJVSViewModel,
            childList: ArrayList<LastJVSChild>,
            position: Int
        ) {
            itemBinding.rvChild.layoutManager = LinearLayoutManager(itemBinding.root.context)
            val adapter =
                LastJVSChildAdapter(itemBinding.root.context, viewModel, childList, position)
            itemBinding.rvChild.adapter = adapter
        }
    }
}
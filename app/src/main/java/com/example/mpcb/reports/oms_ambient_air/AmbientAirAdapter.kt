package com.example.mpcb.reports.oms_ambient_air

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemAmbientAirBinding
import com.example.mpcb.network.request.AmbientAirChild
import com.example.mpcb.network.request.JvsSampleCollectedAirSource


class AmbientAirAdapter(
    val context: Context,
    private val viewModel: OMSAmbientAirViewModel
) : RecyclerView.Adapter<AmbientAirAdapter.AmbientAirViewHolder>() {

    private val parentList = ArrayList<JvsSampleCollectedAirSource>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmbientAirViewHolder {
        val itemBinding = ItemAmbientAirBinding.inflate(mInflater, parent, false)
        return AmbientAirViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AmbientAirViewHolder, position: Int) {
        val item = parentList[position]
        holder.itemBinding.model = item
        holder.setData(viewModel, item.ambientAirChild, position)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = parentList.size

    fun updateList(list: ArrayList<JvsSampleCollectedAirSource>) {
        this.parentList.clear()
        this.parentList.addAll(list)
        notifyDataSetChanged()
    }

    class AmbientAirViewHolder(val itemBinding: ItemAmbientAirBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(
            viewModel: OMSAmbientAirViewModel,
            childList: ArrayList<AmbientAirChild>,
            position: Int
        ) {
            itemBinding.rvChild.layoutManager = LinearLayoutManager(itemBinding.root.context)
            val adapter =
                AmbientAirChildAdapter(itemBinding.root.context, viewModel, childList, position)
            itemBinding.rvChild.adapter = adapter
        }
    }
}
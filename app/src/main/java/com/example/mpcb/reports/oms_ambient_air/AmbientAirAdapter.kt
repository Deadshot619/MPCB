package com.example.mpcb.reports.oms_ambient_air

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemAmbientAirBinding


class AmbientAirAdapter(
    val context: Context,
    private val viewModel: OMSAmbientAirViewModel
) : RecyclerView.Adapter<AmbientAirAdapter.AmbientAirViewHolder>() {

    private val parentList = ArrayList<ArrayList<String>>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmbientAirViewHolder {
        val itemBinding = ItemAmbientAirBinding.inflate(mInflater, parent, false)
        return AmbientAirViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AmbientAirViewHolder, position: Int) {
        holder.setData(viewModel, parentList[position], position)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = parentList.size

    fun updateList(list: ArrayList<ArrayList<String>>) {
        this.parentList.clear()
        this.parentList.addAll(list)
        notifyDataSetChanged()
    }

    class AmbientAirViewHolder(val itemBinding: ItemAmbientAirBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(viewModel: OMSAmbientAirViewModel, childList: ArrayList<String>, position: Int) {
            itemBinding.rvChild.layoutManager = LinearLayoutManager(itemBinding.root.context)
            val adapter = AmbientAirChildAdapter(itemBinding.root.context, viewModel, childList, position)
            itemBinding.rvChild.adapter = adapter
        }
    }
}
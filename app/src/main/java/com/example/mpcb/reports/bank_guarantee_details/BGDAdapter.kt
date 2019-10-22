package com.example.mpcb.reports.bank_guarantee_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemBankGuaranteeBinding


class BGDAdapter(
    val context: Context,
    private val viewModel: BGDViewModel
) : RecyclerView.Adapter<BGDAdapter.BGDViewHolder>() {

    private val sourceList = ArrayList<String>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BGDViewHolder {
        val itemBinding = ItemBankGuaranteeBinding.inflate(mInflater, parent, false)
        return BGDViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BGDViewHolder, position: Int) {
//        val item = visitList[position]
//        holder.itemBinding.model = item
//        holder.itemBinding.viewModel = viewModel

    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = sourceList.size

    fun updateList(list: ArrayList<String>) {
        this.sourceList.clear()
        this.sourceList.addAll(list)
        notifyDataSetChanged()
    }

    class BGDViewHolder(val itemBinding: ItemBankGuaranteeBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
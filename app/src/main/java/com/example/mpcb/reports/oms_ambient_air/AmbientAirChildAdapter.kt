package com.example.mpcb.reports.oms_ambient_air

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemChildAmbientAirBinding
import com.example.mpcb.utils.constants.Constants

class AmbientAirChildAdapter(
    val context: Context,
    private val viewModel: OMSAmbientAirViewModel,
    private val childList: ArrayList<String>,
    private val parentPosition: Int
) : RecyclerView.Adapter<AmbientAirChildAdapter.AmbientAirChildViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmbientAirChildViewHolder {
        val itemBinding = ItemChildAmbientAirBinding.inflate(mInflater, parent, false)
        return AmbientAirChildViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AmbientAirChildViewHolder, position: Int) {
        holder.itemBinding.imgAddMore.setOnClickListener {
            childList.add("child item")
            notifyItemChanged(position)
        }

        holder.itemBinding.imgDelete.setOnClickListener {
            if (childList.size > 1) {
                childList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        val adapter = ArrayAdapter(
            holder.itemBinding.root.context,
            R.layout.simple_spinner_item,
            Constants.PARAMETER_LIST
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.itemBinding.spnParameter.adapter = adapter
        holder.itemBinding.spnParameter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                holder.itemBinding.spnParameter.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = childList.size

    fun updateList(list: ArrayList<ArrayList<String>>) {
//        this.parentList.clear()
//        this.parentList.addAll(list)
//        notifyDataSetChanged()
    }

    class AmbientAirChildViewHolder(val itemBinding: ItemChildAmbientAirBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
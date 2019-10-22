package com.example.mpcb.reports.air_pollution

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemAirPollutionBinding
import com.example.mpcb.utils.constants.Constants


class AirPollutionAdapter(
    val context: Context,
    private val viewModel: AirViewModel
) : RecyclerView.Adapter<AirPollutionAdapter.AirPollutionViewHolder>() {

    private val sourceList = ArrayList<String>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirPollutionViewHolder {
        val itemBinding = ItemAirPollutionBinding.inflate(mInflater, parent, false)
        return AirPollutionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AirPollutionViewHolder, position: Int) {
//        val item = visitList[position]
//        holder.itemBinding.model = item
//        holder.itemBinding.viewModel = viewModel

        holder.setSpinner()

    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = sourceList.size

    fun updateList(list: ArrayList<String>) {
        this.sourceList.clear()
        this.sourceList.addAll(list)
        notifyDataSetChanged()
    }


    class AirPollutionViewHolder(val itemBinding: ItemAirPollutionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner() {
            val adapter = ArrayAdapter(
                itemBinding.root.context,
                android.R.layout.simple_spinner_item,
                Constants.AIR_POLLUTION_LIST
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnSource.adapter = adapter
            itemBinding.spnSource.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    itemBinding.spnSource.setSelection(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }

    }
}
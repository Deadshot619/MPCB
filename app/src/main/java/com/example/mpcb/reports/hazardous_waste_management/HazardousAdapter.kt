package com.example.mpcb.reports.hazardous_waste_management

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemHazardiousReportsBinding
import com.example.mpcb.utils.constants.Constants

class HazardousAdapter(
    val context: Context,
    private val viewModel: HazardousViewModel
) : RecyclerView.Adapter<HazardousAdapter.HazardousViewHolder>() {

    private val visitList = ArrayList<String>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HazardousViewHolder {
        val itemBinding = ItemHazardiousReportsBinding.inflate(mInflater, parent, false)
        return HazardousViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HazardousViewHolder, position: Int) {
//        val item = visitList[position]
//        holder.itemBinding.model = item
//        holder.itemBinding.viewModel = viewModel
        holder.itemBinding.txtReportTitle.text = "Report ${position + 1}"
        holder.itemBinding.imgExpandCollapse.setOnClickListener {
            if (holder.itemBinding.layLinChild.visibility == View.VISIBLE) {
                holder.itemBinding.imgExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        com.example.mpcb.R.drawable.ic_down_arrow
                    )
                )
                holder.itemBinding.layLinChild.visibility = View.GONE
            } else {
                holder.itemBinding.imgExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        com.example.mpcb.R.drawable.ic_up_arrow
                    )
                )
                holder.itemBinding.layLinChild.visibility = View.VISIBLE
            }

//            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, uomList)
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            holder.itemBinding.spnUOM.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                    holder.itemBinding.spnUOM.setSelection(position)
//                    notifyDataSetChanged()
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {}
//            }
        }

        holder.setSpinner()
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = visitList.size

    fun updateList(list: ArrayList<String>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }

    class HazardousViewHolder(val itemBinding: ItemHazardiousReportsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner() {
            val adapter = ArrayAdapter(
                itemBinding.root.context,
                android.R.layout.simple_spinner_item,
                Constants.UNIT_LIST
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnUOM.adapter = adapter
            itemBinding.spnUOM.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    itemBinding.spnUOM.setSelection(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }

    }
}
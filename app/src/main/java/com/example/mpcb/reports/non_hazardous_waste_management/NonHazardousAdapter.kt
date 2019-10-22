package com.example.mpcb.reports.non_hazardous_waste_management

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemNonHazardousReportsBinding
import com.example.mpcb.utils.constants.Constants
import kotlinx.android.synthetic.main.change_password.view.*


class NonHazardousAdapter(
    val context: Context,
    private val viewModel: NonHazardousViewModel
) : RecyclerView.Adapter<NonHazardousAdapter.NonHazardousViewHolder>() {

    private val visitList = ArrayList<String>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonHazardousViewHolder {
        val itemBinding = ItemNonHazardousReportsBinding.inflate(mInflater, parent, false)
        return NonHazardousViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NonHazardousViewHolder, position: Int) {
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

            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, Constants.UNIT_LIST)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.itemBinding.spnUOM.adapter = adapter
            holder.itemBinding.spnUOM.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    holder.itemBinding.spnUOM.setSelection(position)
                    notifyDataSetChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }

    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = visitList.size

    fun updateList(list: ArrayList<String>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }


    class NonHazardousViewHolder(val itemBinding: ItemNonHazardousReportsBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
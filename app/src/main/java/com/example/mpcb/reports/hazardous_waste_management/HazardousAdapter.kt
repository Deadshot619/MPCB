package com.example.mpcb.reports.hazardous_waste_management

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemHazardiousReportsBinding
import com.example.mpcb.network.request.RoutineReportHazardousWaste
import com.example.mpcb.utils.constants.Constants
import java.util.*
import kotlin.collections.ArrayList

class HazardousAdapter(
    val context: Context,
    private val viewModel: HazardousViewModel
) : RecyclerView.Adapter<HazardousAdapter.HazardousViewHolder>() {

    private val visitList = ArrayList<RoutineReportHazardousWaste>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HazardousViewHolder {
        val itemBinding = ItemHazardiousReportsBinding.inflate(mInflater, parent, false)
        return HazardousViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: HazardousViewHolder, position: Int) {
        val item = visitList[position]
        holder.itemBinding.model = item

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
        }

        holder.itemBinding.edtDisposalDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        holder.itemBinding.edtDisposalDate.setText("$dayOfMonth-${month + 1}-$year")
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            datePickerDialog.show()
        }
        holder.setSpinner(item)

        holder.setDataToViews(item)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = visitList.size

    fun updateList(list: ArrayList<RoutineReportHazardousWaste>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }

    class HazardousViewHolder(val itemBinding: ItemHazardiousReportsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner(item: RoutineReportHazardousWaste) {
            val adapter = ArrayAdapter(
                itemBinding.root.context,
                android.R.layout.simple_spinner_item,
                Constants.UNIT_LIST.values.toTypedArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnUOM.adapter = adapter
            itemBinding.spnUOM.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        itemBinding.spnUOM.setSelection(position)
                        item.hwDisposalQuantityUnit = position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
        }

        /**
         * This function is used to set data to views
         */
        fun setDataToViews(item: RoutineReportHazardousWaste) {
            //UOM
            if (item.hwDisposalQuantityUnit != null)
                itemBinding.spnUOM.setSelection(item.hwDisposalQuantityUnit!!)
        }
    }
}
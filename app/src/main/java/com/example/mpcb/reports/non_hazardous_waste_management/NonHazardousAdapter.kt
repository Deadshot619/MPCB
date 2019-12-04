package com.example.mpcb.reports.non_hazardous_waste_management

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemNonHazardousReportsBinding
import com.example.mpcb.network.request.RoutineReportNonHazardousWaste
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.constants.Constants.Companion.UNIT_LIST1
import java.util.*
import kotlin.collections.ArrayList


class NonHazardousAdapter(
    val context: Context,
    private val viewModel: NonHazardousViewModel
) : RecyclerView.Adapter<NonHazardousAdapter.NonHazardousViewHolder>() {

    private val visitList = ArrayList<RoutineReportNonHazardousWaste>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NonHazardousViewHolder {
        val itemBinding = ItemNonHazardousReportsBinding.inflate(mInflater, parent, false)
        return NonHazardousViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NonHazardousViewHolder, position: Int) {
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
                        holder.itemBinding.edtDisposalDate.setText("$year-${month + 1}-$dayOfMonth")
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

    fun updateList(list: ArrayList<RoutineReportNonHazardousWaste>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }


    class NonHazardousViewHolder(val itemBinding: ItemNonHazardousReportsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner(item: RoutineReportNonHazardousWaste) {

            val adapter = ArrayAdapter(
                itemBinding.root.context,
                android.R.layout.simple_spinner_item,
                UNIT_LIST1.values.toTypedArray()
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
                        item.nhwDisposalQuantityUnit = UNIT_LIST1.filterValues { it == Constants.UNIT_LIST[position] }.keys.first()

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
        }

        /**
         * This Method is used to set data to Views
         */
        fun setDataToViews(item: RoutineReportNonHazardousWaste) {
            //UOM
            if (item.nhwDisposalQuantityUnit != null && item.nhwDisposalQuantityUnit != "" )
                itemBinding.spnUOM.setSelection(
                    //Check if the retrieved value is present in the [UNIT_LIST1], return 0 if not present
                    if (UNIT_LIST1[item.nhwDisposalQuantityUnit] != null)
                    //Filter keys in the [UNIT_LIST] according to value in the given position in UNIT_LIST1
                        Constants.UNIT_LIST.filterValues { it == UNIT_LIST1[item.nhwDisposalQuantityUnit] }.keys.first()
                    else 0
                )
        }

    }
}
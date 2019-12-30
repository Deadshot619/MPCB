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
import com.example.mpcb.utils.constants.Constants.Companion.UNIT_LIST
import com.example.mpcb.utils.constants.Constants.Companion.UNIT_LIST1
import com.example.mpcb.utils.constants.Constants.Companion.disableEnableControls
import java.util.*
import kotlin.collections.ArrayList

class HazardousAdapter(
    val context: Context,
    private val viewModel: HazardousViewModel,
    val visitStatus: Boolean
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

        holder.setDataToViews(item, visitStatus)
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
                        item.hwDisposalQuantityUnit = UNIT_LIST1.filterValues { it == UNIT_LIST[position] }.keys.first()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
        }

        /**
         * This function is used to set data to views
         */
        fun setDataToViews(
            item: RoutineReportHazardousWaste,
            visitStatus: Boolean
        ) {
            //UOM spinner
            if (item.hwDisposalQuantityUnit != null && item.hwDisposalQuantityUnit != "")
                itemBinding.spnUOM.setSelection(
                    //Check if the retrieved value is present in the [UNIT_LIST1], return 0 if not present
                    if (UNIT_LIST1[item.hwDisposalQuantityUnit] != null)
                    //Filter keys in the [UNIT_LIST] according to value in the given position in UNIT_LIST1
                        UNIT_LIST.filterValues { it == UNIT_LIST1[item.hwDisposalQuantityUnit] }.keys.first()
                    else 0
                )


            //If true, disable all controls!
            if (visitStatus)
                disableEnableControls(false, itemBinding.layLinChild)
        }
    }
}
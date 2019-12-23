package com.example.mpcb.reports.bank_guarantee_details

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.R
import com.example.mpcb.databinding.ItemBankGuaranteeBinding
import com.example.mpcb.network.request.RoutineReportBankDetail
import java.util.*
import kotlin.collections.ArrayList


class BGDAdapter(
    val context: Context,
    private val viewModel: BGDViewModel,
    private val visitStatus: Boolean
) : RecyclerView.Adapter<BGDAdapter.BGDViewHolder>() {

    private val bankList = ArrayList<RoutineReportBankDetail>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BGDViewHolder {
        val itemBinding = ItemBankGuaranteeBinding.inflate(mInflater, parent, false)
        return BGDViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BGDViewHolder, position: Int) {
        val item = bankList[position]
        holder.itemBinding.model = item

        holder.itemBinding.edtBGDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        holder.itemBinding.edtBGDate.setText("$year-${month + 1}-$dayOfMonth")
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            datePickerDialog.show()
        }
        holder.itemBinding.edtBGValidity.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog =
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        holder.itemBinding.edtBGValidity.setText("$year-${month + 1}-$dayOfMonth")
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
            datePickerDialog.show()
        }
        holder.setListener(item)

        holder.setDataToViews(item, visitStatus)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = bankList.size

    fun updateList(list: ArrayList<RoutineReportBankDetail>) {
        this.bankList.clear()
        this.bankList.addAll(list)
        notifyDataSetChanged()
    }

    class BGDViewHolder(val itemBinding: ItemBankGuaranteeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setListener(item: RoutineReportBankDetail) {
            itemBinding.run{
                rgBGSubmitted.setOnCheckedChangeListener { group, checkedId ->
                    item.bankSubmitted = if (checkedId == R.id.rbSubmittedYes) {
                        txtBGNo.visibility = View.VISIBLE
                        txtBGDate.visibility = View.VISIBLE
                        txtBGValidity.visibility = View.VISIBLE
                        
                        "0"
                    } else {
                        //Hide views
                        txtBGNo.visibility = View.GONE
                        txtBGDate.visibility = View.GONE
                        txtBGValidity.visibility = View.GONE

                        //Set text to empty string
                        edtBGNo.setText("")
                        edtBGDate.setText("")
                        edtBGValidity.setText("")

                        "1"
                    }
                }
            }
        }

        /**
         * This Method is used to set data to Views
         */
        fun setDataToViews(
            item: RoutineReportBankDetail,
            visitStatus: Boolean
        ) {
//            BG Submitted
            if (item.bankSubmitted != "1")
                itemBinding.rgBGSubmitted.check(R.id.rbSubmittedYes)
            else
                itemBinding.rgBGSubmitted.check(R.id.rbSubmittedNo)


            //If true, disable all controls!
            if (visitStatus)
                disableEnableControls(false, itemBinding.categoryParentLay)
        }

        //Method to Enable/Disable Views
        private fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
            for (i in 0 until vg.childCount) {
                val child = vg.getChildAt(i)

                if(child.id != R.id.btnSubmit)
                    child.isEnabled = enable
                if (child is ViewGroup) {
                    disableEnableControls(enable, child)
                }
            }
        }
    }
}
package com.example.mpcb.reports.bank_guarantee_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.R
import com.example.mpcb.databinding.ItemBankGuaranteeBinding
import com.example.mpcb.network.request.RoutineReportBankDetail


class BGDAdapter(
    val context: Context,
    private val viewModel: BGDViewModel
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
        holder.setListener(item)

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
            itemBinding.rgBGSubmitted.setOnCheckedChangeListener { group, checkedId ->
                item.bankSubmitted = if (checkedId == R.id.rbSubmittedYes) "1" else "0"
            }
        }

    }
}
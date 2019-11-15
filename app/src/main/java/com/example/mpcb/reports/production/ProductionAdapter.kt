package com.example.mpcb.reports.production

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemProductionBinding
import com.example.mpcb.network.request.RoutineReportProduct
import com.example.mpcb.utils.constants.Constants


class ProductionAdapter(
    val context: Context,
    private val viewModel: ProductionViewModel
) : RecyclerView.Adapter<ProductionAdapter.ProductionViewHolder>() {

    private val sourceList = ArrayList<RoutineReportProduct>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionViewHolder {
        val itemBinding = ItemProductionBinding.inflate(mInflater, parent, false)
        return ProductionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProductionViewHolder, position: Int) {
        val item = sourceList[position]
        holder.setSpinner(item)
        holder.itemBinding.model = sourceList[position]

    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = sourceList.size

    fun updateList(list: ArrayList<RoutineReportProduct>) {
        this.sourceList.clear()
        this.sourceList.addAll(list)
        notifyDataSetChanged()
    }

    class ProductionViewHolder(val itemBinding: ItemProductionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner(item: RoutineReportProduct) {
            val adapter = ArrayAdapter(
                itemBinding.root.context,
                android.R.layout.simple_spinner_item,
                Constants.UNIT_LIST
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnUnitActual.adapter = adapter
            if (item.productUomActual != null && item.productUomActual != "")
                itemBinding.spnUnitActual.setSelection(item.productUomActual!!.toInt())
            itemBinding.spnUnitActual.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        itemBinding.spnUnitActual.setSelection(position)
                        item.productUomActual = "$position"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }

            itemBinding.spnUnitConsent.adapter = adapter
            if (item.productUom != null && item.productUom != "")
                itemBinding.spnUnitConsent.setSelection(item.productUom!!.toInt())
            itemBinding.spnUnitConsent.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long

                    ) {
                        itemBinding.spnUnitConsent.setSelection(position)
                        item.productUom = "$position"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }

        }

    }
}
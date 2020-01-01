package com.gov.mpcb.reports.production

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemProductionBinding
import com.gov.mpcb.network.request.RoutineReportProduct
import com.gov.mpcb.utils.constants.Constants.Companion.UNIT_LIST
import com.gov.mpcb.utils.constants.Constants.Companion.UNIT_LIST1
import com.gov.mpcb.utils.constants.Constants.Companion.disableEnableControls


class ProductionAdapter(
    val context: Context,
    private val viewModel: ProductionViewModel,
    val visitStatus: Boolean
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
        holder.setDataToViews(visitStatus)
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
                UNIT_LIST1.values.toTypedArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnUnitActual.adapter = adapter
            if (item.productUomActual != null && item.productUomActual != "")
                itemBinding.spnUnitActual.setSelection(
                    //Check if the retrieved value is present in the [UNIT_LIST1], return 0 if not present
                    if (UNIT_LIST1[item.productUomActual] != null)
                        //Filter keys in the [UNIT_LIST] according to value in the given position in UNIT_LIST1
                        try {
                            UNIT_LIST
                                .filterValues { it == UNIT_LIST1[item.productUomActual] }
                                .keys
                                .first()
                        }catch (e: Exception) { 0 }
                     else 0
                )
            itemBinding.spnUnitActual.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        itemBinding.spnUnitActual.setSelection(position)
                        //Retrieve key from [UNIT_LIST1] according to value retrieved from [UNIT_LIST]
                        item.productUomActual = UNIT_LIST1.filterValues { it == UNIT_LIST[position] }.keys.first()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }

            itemBinding.spnUnitConsent.adapter = adapter
            if (item.productUom != null && item.productUom != "")
                itemBinding.spnUnitConsent.setSelection(
                    //Check if the retrieved value is present in the [UNIT_LIST1], return 0 if not present
                    if (UNIT_LIST1[item.productUom] != null)
                        //Filter keys in the [UNIT_LIST] according to value in the given position in UNIT_LIST1
                        UNIT_LIST.filterValues { it == UNIT_LIST1[item.productUom] }.keys.first()
                    else 0
                )
            itemBinding.spnUnitConsent.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long

                    ) {
                        itemBinding.spnUnitConsent.setSelection(position)
                        //Retrieve key from [UNIT_LIST1] according to value retrieved from [UNIT_LIST]
                        item.productUom = UNIT_LIST1.filterValues { it == UNIT_LIST[position] }.keys.first()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

        }

        fun setDataToViews(visitStatus: Boolean) {
            //If true, disable all controls!
            if (visitStatus)
                disableEnableControls(false, itemBinding.categoryParentLay)
        }
    }
}
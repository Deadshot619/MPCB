package com.example.mpcb.reports.oms_ambient_air

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemChildAmbientAirBinding
import com.example.mpcb.network.request.AmbientAirChild
import com.example.mpcb.utils.constants.Constants

class AmbientAirChildAdapter(
    val context: Context,
    private val viewModel: OMSAmbientAirViewModel,
    private val childList: ArrayList<AmbientAirChild>,
    private val parentPosition: Int
) : RecyclerView.Adapter<AmbientAirChildAdapter.AmbientAirChildViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmbientAirChildViewHolder {
        val itemBinding = ItemChildAmbientAirBinding.inflate(mInflater, parent, false)
        return AmbientAirChildViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AmbientAirChildViewHolder, position: Int) {
        val item = childList[position]
        holder.itemBinding.model = item

        holder.itemBinding.imgAddMore.setOnClickListener {
            childList.add(AmbientAirChild())
            notifyItemChanged(position)
        }

        holder.itemBinding.imgDelete.setOnClickListener {
            if (childList.size > 1) {
                childList.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        holder.setSpinner(item)

        holder.setDataToViews(item)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = childList.size

    fun updateList(list: ArrayList<ArrayList<String>>) {
//        this.parentList.clear()
//        this.parentList.addAll(list)
//        notifyDataSetChanged()
    }

    class AmbientAirChildViewHolder(val itemBinding: ItemChildAmbientAirBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner(item: AmbientAirChild) {

            val adapter = ArrayAdapter(
                itemBinding.root.context,
                R.layout.simple_spinner_item,
                Constants.AMBIENT_AIR_PARAM_LIST
            )
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            itemBinding.spnParameter.adapter = adapter

            itemBinding.spnParameter.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        itemBinding.spnParameter.setSelection(position)
                        //save selected position
                        item.position = position
                        item.parameter = itemBinding.spnParameter.selectedItem.toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
        }

        /**
         * This method is used to set the data to the views
         */
        fun setDataToViews(item: AmbientAirChild) {
            itemBinding.run {
                //Parameter
//                if (item.position != "")
                    spnParameter.setSelection(item.position)
                //Prescribed Value
                if (item.prescribedValue != "")
                    edtPrescribedValue.setText(item.prescribedValue)
            }
        }

    }
}
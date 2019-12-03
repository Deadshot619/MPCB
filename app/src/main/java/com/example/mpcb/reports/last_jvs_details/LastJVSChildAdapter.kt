package com.example.mpcb.reports.last_jvs_details

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemChildLastJvsBinding
import com.example.mpcb.network.request.LastJVSChild
import com.example.mpcb.utils.constants.Constants

class LastJVSChildAdapter(
    val context: Context,
    private val viewModel: LastJVSViewModel,
    private val childList: ArrayList<LastJVSChild>,
    private val parentPosition: Int
) : RecyclerView.Adapter<LastJVSChildAdapter.LastJvsChildViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastJvsChildViewHolder {
        val itemBinding = ItemChildLastJvsBinding.inflate(mInflater, parent, false)
        return LastJvsChildViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LastJvsChildViewHolder, position: Int) {
        val item = childList[position]
        holder.itemBinding.model = item

        holder.itemBinding.imgAddMore.setOnClickListener {
            childList.add(LastJVSChild())
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

    class LastJvsChildViewHolder(val itemBinding: ItemChildLastJvsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setSpinner(item: LastJVSChild) {

            val adapter = ArrayAdapter(
                itemBinding.root.context,
                R.layout.simple_spinner_item,
                Constants.JVS_PARAM_LIST.values.toTypedArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
                        item.position = position
                        item.parameter = itemBinding.spnParameter.selectedItem.toString()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
        }

        /**
         * This method is used to set the data to the views
         */
        fun setDataToViews(item: LastJVSChild) {
            itemBinding.run {
                //Parameter
                spnParameter.setSelection(item.position)

                //Prescribed Value
                if (item.prescribedValue != "")
                    edtPrescribedValue.setText(item.prescribedValue)
            }
        }
    }
}
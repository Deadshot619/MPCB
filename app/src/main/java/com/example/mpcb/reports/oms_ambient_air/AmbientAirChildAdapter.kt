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
import com.example.mpcb.utils.constants.Constants.Companion.AMBIENT_AIR_PARAM_LIST
import com.example.mpcb.utils.constants.Constants.Companion.disableEnableControls

class AmbientAirChildAdapter(
    val context: Context,
    private val viewModel: OMSAmbientAirViewModel,
    private val childList: ArrayList<AmbientAirChild>,
    private val parentPosition: Int,
    val visitStatus: Boolean
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

        holder.setDataToViews(item, visitStatus)
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
                AMBIENT_AIR_PARAM_LIST.values.toTypedArray()
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
                        //Set dropdown parameter field value
                        item.parameter = AMBIENT_AIR_PARAM_LIST[position]!!
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }
        }

        /**
         * This method is used to set the data to the views
         */
        fun setDataToViews(
            item: AmbientAirChild,
            visitStatus: Boolean
        ) {
            itemBinding.run {
                //Parameter
//                if (item.position != "")
                    spnParameter.setSelection(
                        //if the given value doesn't match with any value in the hashmap
                        //return 0 else return that Key containing that value
                        if (AMBIENT_AIR_PARAM_LIST.filterValues { it == item.parameter }.keys.isNotEmpty())
                            AMBIENT_AIR_PARAM_LIST.filterValues { it == item.parameter }.keys.first()
                        else 0
                    )


                //Prescribed Value
                if (item.prescribedValue != "")
                    edtPrescribedValue.setText(item.prescribedValue)


                //If true, disable all controls!
                if (visitStatus)
                    disableEnableControls(false, itemBinding.categoryParentLay)
            }
        }

     }
}
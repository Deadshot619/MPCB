package com.example.mpcb.reports.air_pollution

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.R
import com.example.mpcb.databinding.ItemAirPollutionBinding
import com.example.mpcb.network.request.RoutineReportAirPollution
import com.example.mpcb.utils.constants.Constants.Companion.AIR_POLLUTION_LIST


class AirPollutionAdapter(
    val context: Context,
    private val viewModel: AirViewModel,
    private val visitStatus: Boolean
) : RecyclerView.Adapter<AirPollutionAdapter.AirPollutionViewHolder>() {

    private val sourceList = ArrayList<RoutineReportAirPollution>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirPollutionViewHolder {
        val itemBinding = ItemAirPollutionBinding.inflate(mInflater, parent, false)
        return AirPollutionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AirPollutionViewHolder, position: Int) {
        val item = sourceList[position]
        holder.itemBinding.model = item

        holder.setListeners(item)
        holder.setDataToViews(item, visitStatus)

    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = sourceList.size

    fun updateList(list: ArrayList<RoutineReportAirPollution>) {
        this.sourceList.clear()
        this.sourceList.addAll(list)
        notifyDataSetChanged()
    }


    class AirPollutionViewHolder(val itemBinding: ItemAirPollutionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setListeners(item: RoutineReportAirPollution) {
            val adapter = ArrayAdapter(
                itemBinding.root.context,
                android.R.layout.simple_spinner_item,
                AIR_POLLUTION_LIST.values.toTypedArray()
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnSource.adapter = adapter
            itemBinding.spnSource.setSelection(
                //if the given value doesn't match with any value in the hashmap
                //return 0 else return that Key containing that value
                if (AIR_POLLUTION_LIST.filterValues { it == item.airPollutionSource }.keys.isNotEmpty())
                    AIR_POLLUTION_LIST.filterValues { it == item.airPollutionSource }.keys.first()
                else 0
            )
            itemBinding.spnSource.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        item.airPollutionSource = AIR_POLLUTION_LIST[position]!!
                        if (position == 4) {
                            itemBinding.anyOtherLayout.visibility = View.VISIBLE
                            //Retrieve & set data to other Source text
                            itemBinding.edtAnyOther.setText(item.airPollutionSourceOther)
                        } else {
                            itemBinding.anyOtherLayout.visibility = View.GONE
                            itemBinding.edtAnyOther.setText("")
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }

            //Process or Fuel Burning Radio Button
            itemBinding.rgProcessFuelBurning.setOnCheckedChangeListener { group, checkedId ->
                item.airPollutionType = if (checkedId == R.id.rbProcess) {
                    itemBinding.run{
                        //Disable text field
                        edFuelName.isEnabled = false
                        edFuelQuantity.isEnabled = false
                        edFuelUnit.isEnabled = false

                        //Empty Text field
                        edFuelName.setText("")
                        edFuelQuantity.setText("")
                        edFuelUnit.setText("")
                    }
                    "0"
                } else{
                    itemBinding.run{
                        //Enable text field
                        edFuelName.isEnabled = true
                        edFuelQuantity.isEnabled = true
                        edFuelUnit.isEnabled = true
                    }
                    "1"
                }
            }

            itemBinding.cbMechanicalDuster.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionMechDustCollector = if (isChecked) 1 else 0
            }
            itemBinding.cbCycloneDust.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionCycloneDustCollector = if (isChecked) 1 else 0
            }
            itemBinding.cbMultiCycloneDust.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionMultiDustCollector = if (isChecked) 1 else 0
            }
            itemBinding.cbMechanicalDuster.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionMechDustCollector = if (isChecked) 1 else 0
            }
            itemBinding.cbFabricBagFilter.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionFabricBagFilter = if (isChecked) 1 else 0
            }
            itemBinding.cbPackageTower.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionPackageTower = if (isChecked) 1 else 0
            }
            itemBinding.cbVenturiScrubber.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionVenturiScrubber = if (isChecked) 1 else 0
            }
            itemBinding.cbElectroStatic.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionElectroStatic = if (isChecked) 1 else 0
            }
            itemBinding.cbNoProvision.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionNoProvision = if (isChecked) 1 else 0
            }
            itemBinding.cbAnyOther.setOnCheckedChangeListener { buttonView, isChecked ->
                item.airPollutionAnyOther = if (isChecked) 1 else 0
            }

        }

        /**
         * This method is used to set the data to the views
         */
        fun setDataToViews(
            item: RoutineReportAirPollution,
            visitStatus: Boolean
        ) {

            itemBinding.run {
                //Set Source item
                if (item.airPollutionSource != null && item.airPollutionSource != "")
                    spnSource.setSelection(
                        //if the given value doesn't match with any value in the hashmap
                        //return 0 else return that Key containing that value
                        if (AIR_POLLUTION_LIST.filterValues { it == item.airPollutionSource }.keys.isNotEmpty())
                            AIR_POLLUTION_LIST.filterValues { it == item.airPollutionSource }.keys.first()
                        else 0
                    )

                //Set Process/Fuel Burning radio button
                if (item.airPollutionType != "1"){
                    rgProcessFuelBurning.check(R.id.rbProcess)
                }else{
                    rgProcessFuelBurning.check(R.id.rbFuelBurning)
                }

                //Set Process/Fuel Burning
                //Check the checkboxes
                cbMechanicalDuster.isChecked = item.airPollutionMechDustCollector == 1
                cbCycloneDust.isChecked = item.airPollutionCycloneDustCollector == 1
                cbMultiCycloneDust.isChecked = item.airPollutionMultiDustCollector == 1
                cbFabricBagFilter.isChecked = item.airPollutionFabricBagFilter == 1
                cbPackageTower.isChecked = item.airPollutionPackageTower == 1
                cbVenturiScrubber.isChecked = item.airPollutionVenturiScrubber == 1
                cbElectroStatic.isChecked = item.airPollutionElectroStatic == 1
                cbNoProvision.isChecked = item.airPollutionNoProvision == 1
                cbAnyOther.isChecked = item.airPollutionAnyOther == 1
            }

            //If true, disable all controls!
            if (visitStatus)
                disableEnableControls(false, itemBinding.categoryParentLay)
        }

        private fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
            for (i in 0 until vg.childCount) {
                val child = vg.getChildAt(i)

                if(child.id != R.id.btnSubmit)
                    child.isEnabled = enable

                if (child is ViewGroup)
                    disableEnableControls(enable, child)
            }
        }
    }
}
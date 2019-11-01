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
import com.example.mpcb.utils.constants.Constants


class AirPollutionAdapter(
    val context: Context,
    private val viewModel: AirViewModel
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
                Constants.AIR_POLLUTION_LIST
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            itemBinding.spnSource.adapter = adapter
            itemBinding.spnSource.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        itemBinding.spnSource.setSelection(position)
                        item.airPollutionSource = "${position + 1}"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                }

            itemBinding.rgProcessFuelBurning.setOnCheckedChangeListener { group, checkedId ->
                item.airPollutionType = if (checkedId == R.id.rbProcess) "1" else "0"
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

    }
}
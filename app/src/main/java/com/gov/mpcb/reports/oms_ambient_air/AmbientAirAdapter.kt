package com.gov.mpcb.reports.oms_ambient_air

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemAmbientAirBinding
import com.gov.mpcb.network.request.AmbientAirChild
import com.gov.mpcb.network.request.JvsSampleCollectedAirSource
import com.gov.mpcb.utils.constants.Constants.Companion.disableEnableControls


class AmbientAirAdapter(
    val context: Context,
    private val viewModel: OMSAmbientAirViewModel,
    private val visitStatus: Boolean
) : RecyclerView.Adapter<AmbientAirAdapter.AmbientAirViewHolder>() {

    private val parentList = ArrayList<JvsSampleCollectedAirSource>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmbientAirViewHolder {
        val itemBinding = ItemAmbientAirBinding.inflate(mInflater, parent, false)
        return AmbientAirViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AmbientAirViewHolder, position: Int) {
        val item = parentList[position].apply {
            //Use this only when visit status is Visited in which case data is to be displayed from APi
            //or when the data is to be auto filled from APi
            if (visitStatus || jvsAirSourceParameter.size > ambientAirChild.size){
                //If Air Source size is greater than 0 then add that data in 'AmbientAirChil'
                if (this.jvsAirSourceParameter.size > 0) {
                    for (i in 0 until jvsAirSourceParameter.size) {
                        ambientAirChild.add(
                            AmbientAirChild().apply {
                                parameter = jvsAirSourceParameter[i]
                                prescribedValue = jvsAirSourceStdPrescribed[i]
                            }
                        )
                    }
                }
            }
        }



        holder.itemBinding.model = item
        holder.setData(
            viewModel,
//            if (item.ambientAirChild == null)
//                arrayListOf(AmbientAirChild())
//            else
                item.ambientAirChild,
            position,
             visitStatus
        )
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = if (parentList.size == 0 ) 1 else parentList.size

    fun updateList(list: ArrayList<JvsSampleCollectedAirSource>) {
        this.parentList.clear()
        this.parentList.addAll(list)
        notifyDataSetChanged()
    }

    class AmbientAirViewHolder(val itemBinding: ItemAmbientAirBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(
            viewModel: OMSAmbientAirViewModel,
            childList: ArrayList<AmbientAirChild>,
            position: Int,
            visitStatus: Boolean
        ) {
            itemBinding.rvChild.layoutManager =
                LinearLayoutManager(itemBinding.root.context)

            val adapter =
                AmbientAirChildAdapter(
                    itemBinding.root.context,
                    viewModel,
                    childList,
                    position,
                    visitStatus
                )

            itemBinding.rvChild.adapter = adapter


            //If true, disable all controls!
            if (visitStatus)
                disableEnableControls(false, itemBinding.categoryParentLay)
        }
    }
}
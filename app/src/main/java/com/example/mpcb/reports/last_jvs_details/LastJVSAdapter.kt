package com.example.mpcb.reports.last_jvs_details

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemLastJvsBinding
import com.example.mpcb.network.request.JvsSampleCollectedWaterSource
import com.example.mpcb.network.request.LastJVSChild


class LastJVSAdapter(
    val context: Context,
    private val viewModel: LastJVSViewModel,
    val visitStatus: Boolean
) : RecyclerView.Adapter<LastJVSAdapter.LastJvsViewHolder>() {

    private val parentList = ArrayList<JvsSampleCollectedWaterSource>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastJvsViewHolder {
        val itemBinding = ItemLastJvsBinding.inflate(mInflater, parent, false)
        return LastJvsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LastJvsViewHolder, position: Int) {
        val item = parentList[position].apply {
            //Use this only when visit status is Visited & data is to be displayed from APi
            if (visitStatus){
                //If Water Source size is greater than 0 then add that data in 'LastJVSChild'
                if (jvsWaterSourceParameter.size > 0) {
                    for (i in 0 until jvsWaterSourceParameter.size) {
                        lastJvsChild.add(
                            LastJVSChild().apply {
                            parameter = jvsWaterSourceParameter[i]
                            prescribedValue = jvsWaterSourceStdPrescribed[i]
                            }
                        )
                    }
                }
            }
        }


        holder.itemBinding.model = item
        holder.setData(viewModel, item.lastJvsChild, position, visitStatus)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = parentList.size

    fun updateList(list: ArrayList<JvsSampleCollectedWaterSource>) {
        this.parentList.clear()
        this.parentList.addAll(list)
        notifyDataSetChanged()
    }

    class LastJvsViewHolder(val itemBinding: ItemLastJvsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(
            viewModel: LastJVSViewModel,
            childList: ArrayList<LastJVSChild>,
            position: Int,
            visitStatus: Boolean
        ) {
            itemBinding.rvChild.layoutManager = LinearLayoutManager(itemBinding.root.context)
            val adapter =
                LastJVSChildAdapter(itemBinding.root.context, viewModel, childList, position, visitStatus)
            itemBinding.rvChild.adapter = adapter

            //If true, disable all controls!
            if (visitStatus)
                disableEnableControls(false, itemBinding.categoryParentLay)
        }

        private fun disableEnableControls(enable: Boolean, vg: ViewGroup) {
            for (i in 0 until vg.childCount) {
                val child = vg.getChildAt(i)

                if(child.id != com.example.mpcb.R.id.btnSubmit)
                    child.isEnabled = enable
                if (child is ViewGroup) {
                    disableEnableControls(enable, child)
                }
            }
        }
    }
}
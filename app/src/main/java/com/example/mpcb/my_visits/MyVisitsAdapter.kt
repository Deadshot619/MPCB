package com.example.mpcb.my_visits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.R
import com.example.mpcb.databinding.ItemVisitBinding
import com.example.mpcb.network.response.MyVisitModel

class MyVisitsAdapter(
    val context: Context,
    private val viewModel: MyVisitsViewModel
) : RecyclerView.Adapter<MyVisitsAdapter.MyVisitViewHolder>() {

    private val visitList = ArrayList<MyVisitModel>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVisitViewHolder {
        val itemUserBinding = ItemVisitBinding.inflate(mInflater, parent, false)
        return MyVisitViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: MyVisitViewHolder, position: Int) {
        val item = visitList[position]
        holder.itemBinding.model = item
        holder.itemBinding.viewModel = viewModel

        holder.itemBinding.imgExpandCollapse.setOnClickListener {
            if (holder.itemBinding.layLinBottom.visibility == View.VISIBLE) {
                holder.itemBinding.imgExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_down_arrow
                    )
                )
                holder.itemBinding.layLinBottom.visibility = View.GONE
            } else {
                holder.itemBinding.imgExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_up_arrow
                    )
                )
                holder.itemBinding.layLinBottom.visibility = View.VISIBLE
            }
        }

        if (item.checkInStatus == 1) {
            holder.itemBinding.imgLocation.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_location))
        } else {
            holder.itemBinding.imgLocation.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_location_grey))
        }

        if ("Visited" == item.visitStatus) {
            holder.itemBinding.imgReports.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_reports))
        } else {
            holder.itemBinding.imgReports.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_reports_grey))
        }
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    fun updateList(list: ArrayList<MyVisitModel>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = visitList.size

    class MyVisitViewHolder(val itemBinding: ItemVisitBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}
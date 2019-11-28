package com.example.mpcb.my_visits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.R
import com.example.mpcb.databinding.ItemVisitBinding
import com.example.mpcb.network.response.MyVisitModel

class MyVisitsAdapter(
    val context: Context,
    private val viewModel: MyVisitsViewModel
) : RecyclerView.Adapter<MyVisitsAdapter.MyVisitViewHolder>(), Filterable {

    private val visitList = ArrayList<MyVisitModel>()
    private lateinit var visitListFull: ArrayList<MyVisitModel>

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
            holder.itemBinding.imgLocation.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_location
                )
            )
        } else {
            holder.itemBinding.imgLocation.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_location_grey
                )
            )
        }

        if ("Visited" == item.visitStatus) {
            holder.itemBinding.imgReports.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_reports
                )
            )
        } else {
            holder.itemBinding.imgReports.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_reports_grey
                )
            )
        }
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    fun updateList(list: ArrayList<MyVisitModel>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        this.visitListFull = ArrayList(this.visitList)
        notifyDataSetChanged()
    }

    override fun getItemCount() = visitList.size

    class MyVisitViewHolder(val itemBinding: ItemVisitBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    //Filter interface implemented method
    override fun getFilter(): Filter {
        return visitListFilter
    }

    //returns filtered values
    private var visitListFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
//            run only if 'visitListFull' size is > 0
            if (visitListFull.size > 0) {
                //this variable will be used to store & return filered lists
                val filteredList: ArrayList<MyVisitModel> = arrayListOf()

//                check if a character is present in search bar, if not exists then show the original list.
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.addAll(visitListFull)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()
                    for (item in visitListFull) {
                        //Check if the string entered matches with either its Name or ID
                        if (item.industryName.toLowerCase().contains(filterPattern) || item.industryIMISId.toLowerCase().contains(filterPattern))
                            //if the string matches with content in list, add it
                            filteredList.add(item)
                    }
                }

                //wrap filtered list in 'FilterResults
                val results = FilterResults()
                results.values = filteredList

                return results
            }
            return null
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            //Do this if theres a filtered list to be shown
            results?.values?.run {
                visitList.clear()
                visitList.addAll(results.values as Collection<MyVisitModel>)
                notifyDataSetChanged()
            }
        }

    }
}
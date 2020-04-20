package com.gov.mpcb.my_visits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.databinding.ItemNotVisitedBinding
import com.gov.mpcb.databinding.ItemVisitBinding
import com.gov.mpcb.network.response.MyVisitModel

class MyVisitsAdapter(
    val context: Context,
    private val viewModel: MyVisitsViewModel,
    val isUncompletedVisitPresent: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val visitList = ArrayList<MyVisitModel>()
    private lateinit var visitListFull: ArrayList<MyVisitModel>

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //if uncompleted visit are present, then user not_visited layout else use visit_layout
        return if (isUncompletedVisitPresent == 1) {
            UncompletedVisitViewHolder(ItemNotVisitedBinding.inflate(mInflater, parent, false))
        } else {
            MyVisitViewHolder(ItemVisitBinding.inflate(mInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //if uncompleted visit are present, then bind UncompletedVisitViewHolder else bind MyVisitViewHolder
        if (isUncompletedVisitPresent == 1)
            (holder as UncompletedVisitViewHolder).bind(
                visitModel = visitList[position],
                viewModel = viewModel
            )
        else
            (holder as MyVisitViewHolder).bind(
                visitModel = visitList[position],
                viewModel = viewModel,
                context = context
            )
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

    /**
     * ViewHolder for [R.layout.item_visit]
     */
    class MyVisitViewHolder(val itemBinding: ItemVisitBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(visitModel: MyVisitModel, viewModel: MyVisitsViewModel, context: Context) {
            itemBinding.run {
                model = visitModel
                this.viewModel = viewModel

                layoutTop.setOnClickListener {
                    if (layLinBottom.visibility == View.VISIBLE) {
                        itemBinding.tvIndustryNameLabel.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0,
                            R.drawable.ic_down_arrow, 0
                        )
                        layLinBottom.visibility = View.GONE
                    } else {
                        itemBinding.tvIndustryNameLabel.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0,
                            R.drawable.ic_up_arrow, 0
                        )
                        layLinBottom.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    /**
     * ViewHolder for [R.layout.item_not_visited]
     */
    class UncompletedVisitViewHolder(val itemBinding: ItemNotVisitedBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(visitModel: MyVisitModel, viewModel: MyVisitsViewModel) {
            itemBinding.run {
                model = visitModel
                this.viewModel = viewModel
            }
        }
    }

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
                        if (item.industryName.toLowerCase()
                                .contains(filterPattern) || item.industryIMISId.toLowerCase()
                                .contains(filterPattern)
                        )
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
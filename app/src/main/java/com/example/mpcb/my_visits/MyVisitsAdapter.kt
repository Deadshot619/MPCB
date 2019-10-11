package com.example.mpcb.my_visits

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemVisitBinding
import com.example.mpcb.network.response.MyVisitModel
import java.text.SimpleDateFormat

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
        val date = SimpleDateFormat("MMM dd yyyy HH:mm:ss").parse(item.scheduledOn);
        SimpleDateFormat("dd/MM/yyyy").format(date)
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position

    fun updateList(list: ArrayList<MyVisitModel>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = visitList.size

    class MyVisitViewHolder(val itemBinding: ItemVisitBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
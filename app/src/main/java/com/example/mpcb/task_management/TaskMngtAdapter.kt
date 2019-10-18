package com.example.mpcb.task_management

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mpcb.databinding.ItemTaskBinding

class TaskMngtAdapter(
    val context: Context,
    private val viewModel: TaskManagementViewModel
) : RecyclerView.Adapter<TaskMngtAdapter.TaskMngtViewHolder>() {

    private val visitList = ArrayList<String>()
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskMngtViewHolder {
        val itemBinding = ItemTaskBinding.inflate(mInflater, parent, false)
        return TaskMngtViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskMngtViewHolder, position: Int) {
//        val item = visitList[position]
//        holder.itemBinding.model = item
//        holder.itemBinding.viewModel = viewModel

    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = 3

    fun updateList(list: ArrayList<String>) {
        this.visitList.clear()
        this.visitList.addAll(list)
        notifyDataSetChanged()
    }

    class TaskMngtViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
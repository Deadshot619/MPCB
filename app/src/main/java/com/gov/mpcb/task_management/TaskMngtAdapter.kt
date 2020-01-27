package com.gov.mpcb.task_management

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemTaskBinding
import com.gov.mpcb.network.response.TaskDetailsData

class TaskMngtAdapter(
    val context: Context,
    private val viewModel: TaskManagementViewModel
) : RecyclerView.Adapter<TaskMngtAdapter.TaskMngtViewHolder>() {

    //Takes Data of 'ViewTaskDetailsResponse'
    private val tasksList = ArrayList<TaskDetailsData>()

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskMngtViewHolder {
        val itemBinding = ItemTaskBinding.inflate(mInflater, parent, false)
        return TaskMngtViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskMngtViewHolder, position: Int) {
        val item = tasksList[position]
        holder.itemBinding.model = item
//        holder.itemBinding.viewModel = viewModel
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    override fun getItemCount() = tasksList.size

    fun updateList(list: ArrayList<TaskDetailsData>) {
        this.tasksList.clear()
        this.tasksList.addAll(list)
        notifyDataSetChanged()
    }

    class TaskMngtViewHolder(val itemBinding: ItemTaskBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
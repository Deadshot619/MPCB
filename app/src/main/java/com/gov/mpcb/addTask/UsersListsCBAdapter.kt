package com.gov.mpcb.addTask

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemUsersListTaskBinding
import com.gov.mpcb.network.response.UserListTaskResponse

class UsersListsCBAdapter(val context: Context) : RecyclerView.Adapter<UsersListsCBAdapter.UsersListCBViewHolder>(){

    private val usersList = ArrayList<UserListTaskResponse>()

    /**
     * The [UsersListCBViewHolder] constructor takes the binding variable from the associated
     * [ItemUsersListTaskBinding] layout, which nicely gives it access to the full [UserListTaskResponse] information.
     */
    class UsersListCBViewHolder(val binding: ItemUsersListTaskBinding):
        RecyclerView.ViewHolder(binding.root) {    }


    override fun onBindViewHolder(holder: UsersListCBViewHolder, position: Int) {
        val userData = usersList[position]
        holder.binding.userData = userData
    }

    override fun getItemCount(): Int = usersList.size

    fun updateList(list: ArrayList<UserListTaskResponse>){
        this.usersList.clear()
        this.usersList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListCBViewHolder {
        return UsersListCBViewHolder(ItemUsersListTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}


package com.gov.mpcb.addTask

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemUsersListTaskBinding
import com.gov.mpcb.network.response.UserListTaskResponse

class UsersListsCBAdapter(val context: Context, val mViewModel: AddTaskViewModel) : RecyclerView.Adapter<UsersListsCBAdapter.UsersListCBViewHolder>(){

    private val usersList = ArrayList<UserListTaskResponse>()

    /**
     * The [UsersListCBViewHolder] constructor takes the binding variable from the associated
     * [ItemUsersListTaskBinding] layout, which nicely gives it access to the full [UserListTaskResponse] information.
     */
    class UsersListCBViewHolder(val binding: ItemUsersListTaskBinding):
        RecyclerView.ViewHolder(binding.root) {    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListCBViewHolder {
        return UsersListCBViewHolder(
            ItemUsersListTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersListCBViewHolder, position: Int) {
        val userData = usersList[position]

        //Bind userdata in xml
        holder.binding.userData = userData

        //Set listener to checkbox
        holder.binding.cbUserName.setOnClickListener {
            if (holder.binding.cbUserName.isChecked) {
                Toast.makeText(context, "${userData.name} is checked", Toast.LENGTH_LONG).show()
                mViewModel.selectedUsersTemp.add(userData.value)
            }else {
                Toast.makeText(context, "${userData.name} is unChecked", Toast.LENGTH_LONG).show()
                mViewModel.selectedUsersTemp.remove(userData.value)
            }
        }

        //Checks if the current user is previously checked, if yes it checks the user.
        if (mViewModel.userAddedList.contains(userData.value))
            holder.binding.cbUserName.isChecked = true
    }

    override fun getItemCount(): Int = usersList.size

    /**
     * This method is used to update the given list
     */
    fun updateList(list: ArrayList<UserListTaskResponse>){
        this.usersList.clear()
        this.usersList.addAll(list)
        notifyDataSetChanged()
    }

}


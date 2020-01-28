package com.gov.mpcb.addTask

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.databinding.ItemUsersListTaskBinding
import com.gov.mpcb.network.response.UserListTaskResponse

class UsersListsCBAdapter(val context: Context, val mViewModel: AddTaskViewModel) : RecyclerView.Adapter<UsersListsCBAdapter.UsersListCBViewHolder>(){

    //array that Holds users list value
    private val usersList = ArrayList<UserListTaskResponse>()

    //set to hold values of selected users temporarily
    private val selectedUsersTempAdapter = mViewModel.selectedUsersTemp

    /**
     * The [UsersListCBViewHolder] constructor takes the binding variable from the associated
     * [ItemUsersListTaskBinding] layout, which nicely gives it access to the full [UserListTaskResponse] information.
     */
    class UsersListCBViewHolder(val binding: ItemUsersListTaskBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            position: Int,
            userData: UserListTaskResponse,
            mViewModel: AddTaskViewModel
        ) {
            //Bind userdata in xml
            binding.userData = userData

            //Set listener to checkbox
            binding.cbUserName.setOnClickListener {
                if (binding.cbUserName.isChecked) {
//                    Toast.makeText(MPCBApp.instance, "${userData.name} is checked", Toast.LENGTH_LONG).show()
                    mViewModel.selectedUsersTemp.add(userData.value)
                }else {
//                    Toast.makeText(MPCBApp.instance, "${userData.name} is unChecked", Toast.LENGTH_LONG).show()
                    mViewModel.selectedUsersTemp.remove(userData.value)
                }
            }
        }



    }


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

        //Checks if the current user is previously checked, if yes it checks the user.
        holder.binding.cbUserName.isChecked = mViewModel.selectedUsersTemp.contains(userData.value)

        holder.bind(position, userData, mViewModel)
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


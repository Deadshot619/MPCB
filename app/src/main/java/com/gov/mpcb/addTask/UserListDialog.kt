package com.gov.mpcb.addTask

import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.mpcb.databinding.DialogUsersListTaskBinding
import com.gov.mpcb.utils.CommonUtils.dpHeight
import kotlin.math.roundToInt

class UserListDialog(context: Context, val mViewModel: AddTaskViewModel) : DialogFragment() {

    //Inflate Layout
    private var dialogBinding = DialogUsersListTaskBinding.inflate(LayoutInflater.from(context))

    private lateinit var mAdapter: UsersListsCBAdapter

    private val mContext = context


    companion object {
        fun newInstance(
            context: Context,
            mViewModel: AddTaskViewModel
        ): UserListDialog {
            val args = Bundle()
            return UserListDialog(context, mViewModel).apply { arguments = args }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return dialogBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpClickListeners()
        setUpRecyclerView()
        //Set recycler view's height as half of the device height
        dialogBinding.rvUserListData.layoutParams.height = (dpHeight / 2).roundToInt()

        //Update the list
//        mAdapter.updateList(mViewModel.userData)

    }

    /**
     * Method to Setup ClickListeners of the view
     */
    private fun setUpClickListeners() {
        //Close Button
        dialogBinding.ivBtnClose.setOnClickListener {
            this.dismiss()
        }

        //Add Button
        dialogBinding.btnAdd.setOnClickListener {
            mViewModel.addCheckedUserToListSet(mViewModel.selectedUsersTemp)
            //Method to set text on User Details View
            mViewModel.setText(mViewModel.userAddedList.size)
            dismiss()
        }

        //Clear All button
        dialogBinding.btnClearAll.setOnClickListener{
            mViewModel.run{
                selectedUsersTemp.clear()
                addCheckedUserToListSet(selectedUsersTemp)
                setText(mViewModel.userAddedList.size)
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Method to setUp RecyclerView
     */
    private fun setUpRecyclerView() {
        //Set adapter to recycler view
        mAdapter = UsersListsCBAdapter(mContext, mViewModel)

        dialogBinding.rvUserListData.run {
            //Set divider to recycler view
            addItemDecoration(
                DividerItemDecoration(
                    mContext,
                    LinearLayoutManager.VERTICAL
                )
            )

            //Set LayoutManager to recycler view
            layoutManager = LinearLayoutManager(mContext)
            setHasFixedSize(true)
            //Set adapter to recycler view
            adapter = mAdapter.apply { updateList(mViewModel.userData) }
        }
    }

    override fun onStart() {
        super.onStart()

        //Make Dialog bg transparent & width to match_parent
        dialog?.window?.run {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT ,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            setBackgroundDrawable(ColorDrawable(TRANSPARENT))
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}
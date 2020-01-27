package com.gov.mpcb.addTask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.gov.mpcb.databinding.DialogUsersListTaskBinding

class UserListDialog (context: Context, val mViewModel: AddTaskViewModel): DialogFragment() {

    //Inflate Layout
    private var dialogBinding = DialogUsersListTaskBinding.inflate(LayoutInflater.from(context))

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

        if (dialog != null && dialog!!.window != null)
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)

        return dialogBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        dialogBinding.model = model
//        dialogBinding.viewModel = mViewModel

    }
}
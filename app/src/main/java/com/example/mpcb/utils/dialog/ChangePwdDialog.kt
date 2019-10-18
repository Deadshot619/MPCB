package com.example.mpcb.utils.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.example.mpcb.databinding.ChangePasswordBinding
import com.example.mpcb.network.request.ChangePwdRequest
import com.example.mpcb.profile.ProfileViewModel

class ChangePwdDialog(context: Context, val mViewModel: ProfileViewModel) : AppCompatDialog(context) {

    private var dialogBinding = ChangePasswordBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogBinding.root)
        setCancelable(false)
        window!!.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialogBinding.model = ChangePwdRequest()
        dialogBinding.viewModel = mViewModel
        dialogBinding.btnCancel.setOnClickListener { dismiss() }
    }


}
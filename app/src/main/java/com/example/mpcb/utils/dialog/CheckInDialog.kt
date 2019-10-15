package com.example.mpcb.utils.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.example.mpcb.databinding.CheckInPopupBinding
import com.example.mpcb.my_visits.MyVisitsViewModel
import com.example.mpcb.network.response.MyVisitModel

class CheckInDialog(context: Context, val model: MyVisitModel, val mViewModel: MyVisitsViewModel) :
    AppCompatDialog(context) {

    private var dialogBinding = CheckInPopupBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogBinding.root)
        setCancelable(false)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setLayout(WRAP_CONTENT, WRAP_CONTENT)
        dialogBinding.model = model
        dialogBinding.viewModel = mViewModel
        dialogBinding.btnCancel.setOnClickListener { dismiss() }
    }


}
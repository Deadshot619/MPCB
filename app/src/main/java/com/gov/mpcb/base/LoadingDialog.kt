package com.gov.mpcb.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.gov.mpcb.databinding.DialogProgressBinding

class LoadingDialog(context: Context) : AppCompatDialog(context) {

    private var dialogProgressBinding: DialogProgressBinding =
        DialogProgressBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogProgressBinding.root)
        setCancelable(false)
        window!!.setLayout(MATCH_PARENT, WRAP_CONTENT)
    }

    fun setMessage(msg: String) {
        dialogProgressBinding.txtMessage.text = msg
    }

}
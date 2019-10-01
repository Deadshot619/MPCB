package com.example.mpcb.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import com.example.mpcb.databinding.DialogProgressBinding
import java.util.*

class LoadingDialog(context: Context) : AppCompatDialog(context) {

    private var dialogProgressBinding: DialogProgressBinding =
        DialogProgressBinding.inflate(LayoutInflater.from(context))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(dialogProgressBinding.root)
        Objects.requireNonNull(window).setLayout(WRAP_CONTENT, WRAP_CONTENT)
    }

    fun setMessage(msg: String) {
        dialogProgressBinding.txtMessage.text = msg
    }

}
package com.example.mpcb.utils.dialog

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog

class DialogHelper {

    companion object{


        fun displayDialog(context: Context, view: View, title:String="",cancellable: Boolean=false):AlertDialog{

            var builder :AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setTitle(title)
            builder.setCancelable(cancellable)

            return builder.show()
        }

    }

}
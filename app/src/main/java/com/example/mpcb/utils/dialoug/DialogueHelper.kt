package com.example.mpcb.utils.dialoug

import android.content.Context
import android.view.View
import androidx.appcompat.app.AlertDialog

class DialogueHelper {

    companion object{


        fun displayDialoug(context: Context, view: View, title:String="",cancellable: Boolean=false):AlertDialog{

            var builder :AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setTitle(title)
            builder.setCancelable(cancellable)

            return builder.show()
        }

    }

}
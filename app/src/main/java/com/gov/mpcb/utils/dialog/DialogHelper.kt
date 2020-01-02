package com.gov.mpcb.utils.dialog

import android.content.Context
import android.content.Intent
import android.provider.Settings
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

        fun showLocationAlertDialog(context: Context) {
            AlertDialog.Builder(context).apply {
                setMessage("Please Turn On Location/GPS Service")
                setPositiveButton("OK") { _, _ ->
                    val viewIntent =
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)  //take to setting page to turn on the location
                    context.startActivity(viewIntent)
                }
                setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
        }

    }



}
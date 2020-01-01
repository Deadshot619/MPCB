package com.gov.mpcb.utils.log

import android.content.Context
import android.util.Log

class LogHelper {

    companion object {

        val TAG_DATA: String = "myTag"
        val TAG_CONTEXT_DATA: String = "tagContx"
        val TAG_ERROR: String = "errorTag"

        fun showLogData(message: String){
            Log.d(TAG_DATA,message)
        }


        fun showLogError(message: String) {
            Log.d(TAG_ERROR, "Error: $message")
        }

        fun showContextLogData(context: Context, s: String) {
            Log.d(TAG_CONTEXT_DATA, String.format("%s\t%s", context.javaClass.canonicalName, s))
        }

    }

}
package com.gov.mpcb.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import com.gov.mpcb.base.MPCBApp
import java.util.*

enum class LoadingStatus { LOADING, ERROR, DONE }
enum class ListData { LOADING, PRESENT, EMPTY }

object CommonUtils {
    //Get Device's Display Metrics
    private val displayMetrics = MPCBApp.instance.resources.displayMetrics

    //Get Device's height in dp
    val dpHeight: Float
        get() = displayMetrics.heightPixels / displayMetrics.density

    //Get Device's width in dp
    val dpWidth: Float
        get() = displayMetrics.widthPixels / displayMetrics.density

    /**
     * This method is used to show Date Picker Dialog. This will set the date in the
     * given view/[id].
     *
     * @param context Takes a context as parameter
     * @param id Takes an EditText view as input
     */
    fun showDateDialog(context: Context, id: EditText, hidePreviousDates: Boolean = false) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                id.setText("$year-${month + 1}-$dayOfMonth")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply { if (hidePreviousDates) datePicker.minDate = calendar.timeInMillis - 1000 }.show()
    }
}
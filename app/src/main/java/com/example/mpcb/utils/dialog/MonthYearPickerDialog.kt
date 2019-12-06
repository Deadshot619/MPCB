package com.example.mpcb.utils.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.example.mpcb.R
import java.util.*


class MonthYearPickerDialog : DialogFragment() {
    private var listener: DatePickerDialog.OnDateSetListener? = null

    fun setListener(listener: DatePickerDialog.OnDateSetListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        // Get the layout inflater
        val inflater = activity!!.layoutInflater

        val cal = Calendar.getInstance()

        val dialog = inflater.inflate(R.layout.dialog_month_year, null)
        val monthPicker = dialog.findViewById(R.id.picker_month) as NumberPicker
        val yearPicker = dialog.findViewById(R.id.picker_year) as NumberPicker

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = cal.get(Calendar.MONTH)

        val year = cal.get(Calendar.YEAR)
        yearPicker.minValue = 2016
        yearPicker.maxValue = year
        yearPicker.value = year

        builder.setView(dialog)
            // Add action buttons
            .setPositiveButton(
                "OK"
            ) { _, id ->

                listener!!.onDateSet(
                    null,
                    yearPicker.value,
                    monthPicker.value,
                    0
                )
            }
            .setNegativeButton(
                "CANCEL"
            ) { _, id ->
                this@MonthYearPickerDialog.dialog!!.cancel()
            }
        return builder.create()
    }

    companion object {
        private val MAX_YEAR = 2099
    }
}
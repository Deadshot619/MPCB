package com.gov.mpcb.utils.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.gov.mpcb.R
import com.gov.mpcb.utils.constants.Constants
import java.util.*


class MonthYearPickerDialog() : DialogFragment() {
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

        val year = cal.get(Calendar.YEAR)
        yearPicker.minValue = 2016
        yearPicker.maxValue = year//                yearDashboard = yearPicker.value
//                monthDashboard = monthPicker.value

        //Set values to month & year according to calendarConstant
        when (calendarConstant) {
            Constants.Companion.CalendarConstant.DASHBOARD -> {
                monthPicker.value = if (monthDashboard >= 0) monthDashboard else cal.get(Calendar.MONTH) + 1
                yearPicker.value = if (yearDashboard >= 0) yearDashboard else year
            }
            Constants.Companion.CalendarConstant.MY_VISIT -> {
                monthPicker.value = if (monthMyVisit >= 0) monthMyVisit else cal.get(Calendar.MONTH) + 1
                yearPicker.value = if (yearMyVisit >= 0) yearMyVisit else year
            }
        }

        // Add action buttons
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

//                yearDashboard = yearPicker.value
//                monthDashboard = monthPicker.value
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

        //Initialize this with 'CalendarConstant.DASHBOARD'
        var calendarConstant: Constants.Companion.CalendarConstant =
            Constants.Companion.CalendarConstant.DASHBOARD

        //For Dashboard
        var yearDashboard = -1
        var monthDashboard = -1

        //For MyVisit
        var yearMyVisit = -1
        var monthMyVisit = -1
    }
}
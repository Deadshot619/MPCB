package com.example.mpcb.reports.previous_legal_actions


import android.app.DatePickerDialog
import android.util.Log
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentPreviousLegalBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import java.util.*

class PreviousLegalFragment : BaseFragment<FragmentPreviousLegalBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_previous_legal
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@PreviousLegalFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_16)

        mBinding.edtActionInitiated.setOnClickListener { showDateDialog() }

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.e("Date", "" + year + " " + (month + 1) + " " + dayOfMonth)
                    mBinding.edtActionInitiated.setText("$year-${month + 1}-$dayOfMonth")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    private fun onSubmit() {
        report.data.routineReport.actionInitiatedDate = mBinding.edtActionInitiated.text.toString()
        report.data.routineReport.specialCompliance = mBinding.edtSpecificCompliance.text.toString()

        if (validate()) {
            saveReportData()
            addReportFragment(Constants.REPORT_17)
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.actionInitiatedDate.isNullOrEmpty()) {
            showMessage("Enter Action Initiated Date")
            return false
        }
        if (report.data.routineReport.specialCompliance.isNullOrEmpty()) {
            showMessage("Enter Specific Compliance")
            return false
        }

        return true
    }

}
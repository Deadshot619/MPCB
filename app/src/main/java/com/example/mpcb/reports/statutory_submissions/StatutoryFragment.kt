package com.example.mpcb.reports.statutory_submissions


import android.app.DatePickerDialog
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentStatutoryBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import java.util.*


class StatutoryFragment : BaseFragment<FragmentStatutoryBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private val HAZARDOUS_WASTE = 1
    private val ENVIRONMENT_REPORT = 2

    override fun getLayoutId() = R.layout.fragment_statutory
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@StatutoryFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_15)

        mBinding.edtHazardousWaste.setOnClickListener { showDateDialog(HAZARDOUS_WASTE) }
        mBinding.edtEnvironmentReport.setOnClickListener { showDateDialog(ENVIRONMENT_REPORT) }
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun showDateDialog(id: Int) {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    when (id) {
                        HAZARDOUS_WASTE -> mBinding.edtHazardousWaste.setText("$dayOfMonth-${month + 1}-$year")
                        ENVIRONMENT_REPORT -> mBinding.edtEnvironmentReport.setText("$dayOfMonth-${month + 1}-$year")
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    private fun onSubmit() {
        report.data.routineReport.hwAnnualReturnDate = mBinding.edtHazardousWaste.text.toString()
        report.data.routineReport.envStatementReport = mBinding.edtEnvironmentReport.text.toString()

        saveReportData()
        addReportFragment(Constants.REPORT_16)
    }


}
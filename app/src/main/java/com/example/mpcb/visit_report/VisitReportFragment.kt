package com.example.mpcb.visit_report


import android.content.Context
import android.content.Intent
import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentVisitReportBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage


class VisitReportFragment : BaseFragment<FragmentVisitReportBinding, VisitReportViewModel>(), VisitReportNavigator {

    override fun getLayoutId() = R.layout.fragment_visit_report
    override fun getViewModel() = VisitReportViewModel::class.java
    override fun getNavigator() = this@VisitReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.toolbarLayout.visitId.text = "#32133232"
        mBinding.toolbarLayout.visitName.text = "Johnson Controls-Hitachi Air Conditioning India Ltd"

        mBinding.itemListener = ReportItemListener(activity as Context)
    }


}

class ReportItemListener(val context: Context) {

    fun onClick(v: View) {
        val reportIntent = Intent(context, ReportsPageActivity::class.java)
        when (v.id) {
            R.id.industryTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_1)
            }
            R.id.productTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_2)
            }
            R.id.treatmentTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_3)
            }
            R.id.waterWasteTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_4)
            }
            R.id.disposalTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_5)
            }
            R.id.omsWaterTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_6)
            }
            R.id.electricTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_7)
            }
            R.id.lastJVSTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_8)
            }
            R.id.airPollutionTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_9)
            }
            R.id.omsStackTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_10)
            }
            R.id.omsAmbientTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_11)
            }
            R.id.hazardousTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_12)
            }
            R.id.nonHazardousTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_13)
            }
            R.id.treeTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_14)
            }
            R.id.statutoryTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_15)
            }
            R.id.previousTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_16)
            }
            R.id.bankGuaranteeTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_17)
            }
            R.id.additionalTag -> {
                reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_18)
            }
        }
        context.startActivity(reportIntent)
    }
}



package com.example.mpcb.visit_report


import android.content.Context
import android.content.Intent
import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentVisitReportBinding
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getReportFlagStatus
import com.example.mpcb.utils.showMessage


class VisitReportFragment : BaseFragment<FragmentVisitReportBinding, VisitReportViewModel>(),
    VisitReportNavigator {

    private lateinit var visitItem: MyVisitModel

    override fun getLayoutId() = R.layout.fragment_visit_report
    override fun getViewModel() = VisitReportViewModel::class.java
    override fun getNavigator() = this@VisitReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        if (arguments != null && arguments!!.getParcelable<MyVisitModel>(Constants.VISIT_ITEM_KEY) != null) {
            visitItem = arguments!!.getParcelable(Constants.VISIT_ITEM_KEY)!!
        }


        mBinding.toolbarLayout.visitId.text = "#${visitItem.industryIMISId}"
        mBinding.toolbarLayout.visitName.text = visitItem.industryName

        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }


        mBinding.itemListener = ReportItemListener(getBaseActivity(), visitItem)
    }


    override fun onStart() {
        super.onStart()
        displayCheckImage()
    }


    /**
     * This method checks the value of report complete flag.
     * if true, this makes the check image visible which indicates the report is completely filled
     */
    private fun displayCheckImage(){
        for (i in 1..18){
            when (i){
//                1 -> if (true) mBinding.ivIndustryTag.visibility = View.VISIBLE
                1 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_1))
                    mBinding.ivIndustryTag.visibility = View.VISIBLE
                2 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_2))
                    mBinding.ivProductTag.visibility = View.VISIBLE
                3 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_3))
                    mBinding.ivTreatmentTag.visibility = View.VISIBLE
                4 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_4))
                    mBinding.ivWaterWasteTag.visibility = View.VISIBLE
                5 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_5))
                    mBinding.ivDisposalTag.visibility = View.VISIBLE
                6 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_6))
                    mBinding.ivOmsWaterTag.visibility = View.VISIBLE
                7 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_7))
                    mBinding.ivElectricTag.visibility = View.VISIBLE
                8 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_8))
                    mBinding.ivLastJVSTag.visibility = View.VISIBLE
                9 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_9))
                    mBinding.ivAirPollutionTag.visibility = View.VISIBLE
                10 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_10))
                    mBinding.ivOmsStackTag.visibility = View.VISIBLE
                11 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_11))
                    mBinding.ivOmsAmbientTag.visibility = View.VISIBLE
                12 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_12))
                    mBinding.ivHazardoudTag.visibility = View.VISIBLE
                13 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_13))
                    mBinding.ivNonHazardousTag.visibility = View.VISIBLE
                14 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_14))
                    mBinding.ivTreeTag.visibility = View.VISIBLE
                15 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_15))
                    mBinding.ivStatutoryTag.visibility = View.VISIBLE
                16 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_16))
                    mBinding.ivPreviousTag.visibility = View.VISIBLE
                17 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_17))
                    mBinding.ivBankGuaranteeTag.visibility = View.VISIBLE
                18 -> if (getReportFlagStatus(visitItem.industryIMISId, Constants.REPORT_18))
                    mBinding.ivAdditionalTag.visibility = View.VISIBLE
            }
        }
    }

}

class ReportItemListener(
    val context: Context,
    val visitItem: MyVisitModel
) {

    fun onClick(v: View) {
        val reportIntent = Intent(context, ReportsPageActivity::class.java)
        reportIntent.putExtra(Constants.VISIT_REPORT_ID, visitItem.industryIMISId)
        PreferencesHelper.setLongPreference(Constants.VISIT_ID, visitItem.visitSchedulerId)
        PreferencesHelper.setStringPreference(Constants.INDUS_IMIS_ID, visitItem.industryIMISId)
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



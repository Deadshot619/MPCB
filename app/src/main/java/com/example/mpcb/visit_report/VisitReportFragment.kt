package com.example.mpcb.visit_report


import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentVisitReportBinding
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getReportFlagStatus
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.toast


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
        mBinding.run {
            for (i in 1..18) {
                when (i) {
//                1 -> if (true) mBinding.ivIndustryTag.visibility = View.VISIBLE
                    1 -> if (getFlagStatus(Constants.REPORT_1))
                        ivIndustryTag.visibility = View.VISIBLE
                    2 -> if (getFlagStatus(Constants.REPORT_2))
                        ivProductTag.visibility = View.VISIBLE
                    3 -> if (getFlagStatus(Constants.REPORT_3))
                        ivTreatmentTag.visibility = View.VISIBLE
                    4 -> if (getFlagStatus(Constants.REPORT_4))
                        ivWaterWasteTag.visibility = View.VISIBLE
                    5 -> if (getFlagStatus(Constants.REPORT_5))
                        ivDisposalTag.visibility = View.VISIBLE
                    6 -> if (getFlagStatus(Constants.REPORT_6))
                        ivOmsWaterTag.visibility = View.VISIBLE
                    7 -> if (getFlagStatus(Constants.REPORT_7))
                        ivElectricTag.visibility = View.VISIBLE
                    8 -> if (getFlagStatus(Constants.REPORT_8))
                        ivLastJVSTag.visibility = View.VISIBLE
                    9 -> if (getFlagStatus(Constants.REPORT_9))
                        ivAirPollutionTag.visibility = View.VISIBLE
                    10 -> if (getFlagStatus(Constants.REPORT_10))
                        ivOmsStackTag.visibility = View.VISIBLE
                    11 -> if (getFlagStatus(Constants.REPORT_11))
                        ivOmsAmbientTag.visibility = View.VISIBLE
                    12 -> if (getFlagStatus(Constants.REPORT_12))
                        ivHazardoudTag.visibility = View.VISIBLE
                    13 -> if (getFlagStatus(Constants.REPORT_13))
                        ivNonHazardousTag.visibility = View.VISIBLE
                    14 -> if (getFlagStatus(Constants.REPORT_14))
                        ivTreeTag.visibility = View.VISIBLE
                    15 -> if (getFlagStatus(Constants.REPORT_15))
                        ivStatutoryTag.visibility = View.VISIBLE
                    16 -> if (getFlagStatus(Constants.REPORT_16))
                        ivPreviousTag.visibility = View.VISIBLE
                    17 -> if (getFlagStatus(Constants.REPORT_17))
                        ivBankGuaranteeTag.visibility = View.VISIBLE
                    18 -> if (getFlagStatus(Constants.REPORT_18))
                        ivAdditionalTag.visibility = View.VISIBLE
                }
            }
        }
    }

    /**
     * This function is used to get Flag Status of Reports
     *
     * @param reportNumber Takes a Constant.Report_Number as input.
     * @return [Boolean] returns the state of of form. 1 if completed otherwise 0.
     */
    private fun getFlagStatus(reportNumber: Int): Boolean{
        return getReportFlagStatus(visitItem.industryIMISId, reportNumber)
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
                if (getFlagStatus(Constants.REPORT_1))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_2)
                else{
                    showMessage()
                    return
                }
            }
            R.id.treatmentTag -> {
                if (getFlagStatus(Constants.REPORT_2))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_3)
                else{
                    showMessage()
                    return
                }
            }
            R.id.waterWasteTag -> {
                if (getFlagStatus(Constants.REPORT_3))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_4)
                else{
                    showMessage()
                    return
                }
            }
            R.id.disposalTag -> {
                if (getFlagStatus(Constants.REPORT_4))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_5)
                else{
                    showMessage()
                    return
                }
            }
            R.id.omsWaterTag -> {
                if (getFlagStatus(Constants.REPORT_5))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_6)
                else{
                    showMessage()
                    return
                }
            }
            R.id.electricTag -> {
                if (getFlagStatus(Constants.REPORT_6))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_7)
                else{
                    showMessage()
                    return
                }
            }
            R.id.lastJVSTag -> {
                if (getFlagStatus(Constants.REPORT_7))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_8)
                else{
                    showMessage()
                    return
                }
            }
            R.id.airPollutionTag -> {
                if (getFlagStatus(Constants.REPORT_8))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_9)
                else{
                    showMessage()
                    return
                }
            }
            R.id.omsStackTag -> {
                if (getFlagStatus(Constants.REPORT_9))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_10)
                else{
                    showMessage()
                    return
                }
            }
            R.id.omsAmbientTag -> {
                if (getFlagStatus(Constants.REPORT_10))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_11)
                else{
                    showMessage()
                    return
                }
            }
            R.id.hazardousTag -> {
                if (getFlagStatus(Constants.REPORT_11))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_12)
                else{
                    showMessage()
                    return
                }
            }
            R.id.nonHazardousTag -> {
                if (getFlagStatus(Constants.REPORT_12))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_13)
                else{
                    showMessage()
                    return
                }
            }
            R.id.treeTag -> {
                if (getFlagStatus(Constants.REPORT_13))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_14)
                else{
                    showMessage()
                    return
                }
            }
            R.id.statutoryTag -> {
                if (getFlagStatus(Constants.REPORT_14))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_15)
                else{
                    showMessage()
                    return
                }
            }
            R.id.previousTag -> {
                if (getFlagStatus(Constants.REPORT_15))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_16)
                else{
                    showMessage()
                    return
                }
            }
            R.id.bankGuaranteeTag -> {
                if (getFlagStatus(Constants.REPORT_16))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_17)
                else{
                    showMessage()
                    return
                }
            }
            R.id.additionalTag -> {
                if (getFlagStatus(Constants.REPORT_17))
                    reportIntent.putExtra(Constants.REPORTS_PAGE_KEY, Constants.REPORT_18)
                else{
                    showMessage()
                    return
                }
            }
        }
        context.startActivity(reportIntent)
    }

    /**
     * This method is used to get Flag Status of Reports
     *
     * @param reportNumber Takes a Constant.Report_Number as input.
     * @return [Boolean] returns the state of of form. 1 if completed otherwise 0.
     */
    private fun getFlagStatus(reportNumber: Int): Boolean{
        return getReportFlagStatus(visitItem.industryIMISId, reportNumber)
    }

    /**
     * This method is used to display message to user regarding Reports
     */
    private fun showMessage() {
        if (toast != null){
            toast?.cancel()
        }
        toast = Toast.makeText(context, "Please complete previous Report first", Toast.LENGTH_SHORT)
        toast?.show()
    }
}



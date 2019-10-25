package com.example.mpcb.reports.industry

import android.widget.ArrayAdapter
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentIndustryCategoryBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage
import com.google.gson.Gson

class IndustryReportFragment :
    BaseFragment<FragmentIndustryCategoryBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_industry_category
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@IndustryReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_1)

        val prefRequest = ReportRequest()


        val adapter = ArrayAdapter(
            getBaseActivity(),
            android.R.layout.simple_spinner_item,
            Constants.CATEGORY_LIST
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.catSpinner.adapter = adapter


        mBinding.rgConsent.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                1 -> prefRequest.data.routineReport.consentObtain = 1
                2 -> prefRequest.data.routineReport.consentObtain = 0
            }
        }

        mBinding.btnSubmit.setOnClickListener {


            prefRequest.data.industryCategoryReselect =
                "${mBinding.catSpinner.selectedItemPosition + 1}"
            prefRequest.data.routineReport.visitedOn = mBinding.visitCatDateEd.text.toString()
            prefRequest.data.routineReport.emailAddress = mBinding.visitCatEmailEd.text.toString()
            prefRequest.data.routineReport.telephoneNumber =
                mBinding.visitCatTelephoneEd.text.toString()
            prefRequest.data.routineReport.validityOfConsentUpto =
                mBinding.consentVaildDateEd.text.toString()
            prefRequest.data.routineReport.validityOfConsentIe =
                mBinding.consentIeEd.text.toString()
            prefRequest.data.routineReport.hwOfValidUptoDe = mBinding.consentDeEd.text.toString()
            PreferencesHelper.setPreferences(Constants.REPORT_KEY, Gson().toJson(prefRequest))

            addReportFragment(Constants.REPORT_2)
        }
    }
}
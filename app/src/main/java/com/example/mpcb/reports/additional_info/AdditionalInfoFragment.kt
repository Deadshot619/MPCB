package com.example.mpcb.reports.additional_info


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAdditionalInfoBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class AdditionalInfoFragment :
    BaseFragment<FragmentAdditionalInfoBinding, AdditionalInfoViewModel>(),
    AdditionalInfoNavigator {

    override fun getLayoutId() = R.layout.fragment_additional_info
    override fun getViewModel() = AdditionalInfoViewModel::class.java
    override fun getNavigator() = this@AdditionalInfoFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_18)
        setListener()

        mBinding.btnSubmit.setOnClickListener { onSubmit() }

    }

    private fun setListener() {
        mBinding.rgUnitComplied.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.legalActionUnitComplied =
                if (checkedId == R.id.rbUnitYes) 1 else 0
        }
    }

    private fun onSubmit() {
        report.data.routineReport.additionalInfo = mBinding.edtAddInfo.text.toString()

        if (validate()) {
            saveReportData(
                reportKey = Constants.REPORT_18,
                reportStatus = true
            )
            mViewModel.submitReport()
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.additionalInfo.isNullOrEmpty()) {
            showMessage("Enter Additional Information")
            return false
        }
        if (report.data.routineReport.legalActionUnitComplied == null) {
            showMessage("Select Unit Compiled")
            return false
        }
        return true
    }

    override fun onSubmitReportSuccess(msg: String) {
        showMessage(msg)
        activity!!.finish()
    }
}
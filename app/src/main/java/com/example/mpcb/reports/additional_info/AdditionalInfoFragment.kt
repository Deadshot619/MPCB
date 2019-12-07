package com.example.mpcb.reports.additional_info


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAdditionalInfoBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getBooleanPreference
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getReportFlagStatus
import com.example.mpcb.utils.showMessage

class AdditionalInfoFragment :
    BaseFragment<FragmentAdditionalInfoBinding, AdditionalInfoViewModel>(),
    AdditionalInfoNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_additional_info
    override fun getViewModel() = AdditionalInfoViewModel::class.java
    override fun getNavigator() = this@AdditionalInfoFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_18)
        setListener()


        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage("${getBooleanPreference(Constants.VISIT_STATUS)}")
        //set report variable data
        setReportVariableData(visitReportId)


        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.apply {
                text = "Done"
                setOnClickListener { activity?.finish() }
            }
        }
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
                reportNo = visitReportId,
                reportKey = Constants.REPORT_18,
                reportStatus = true
            )

            //submit report only if all the reports are filled.
            if (checkIfReportsFilled()) {
                mViewModel.submitReport(reportRequest = getReportData(visitReportId))
            } else {
                showMessage("Please fill all the reports!")
            }

        }
    }

    /**
     * This method is used to check if all the reports are filled.
     *  @return returns false if even one of the report is not filled. otherwise true
     */
    private fun checkIfReportsFilled(): Boolean {
        //"i" represents report key
        for (i in 1..18) {
            if (!getReportFlagStatus(visitReportId, i)) {
                return false
            }
        }
        return true
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

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if (reports != null) {
            mBinding.run {
                reports?.data?.routineReport?.run {
                    //Additional Info
                    edtAddInfo.setText(additionalInfo)

                    //Whether Unit Complied
                    if (legalActionUnitComplied == 1)
                        rgUnitComplied.check(R.id.rbUnitYes)
                    else
                        rgUnitComplied.check(R.id.rbUnitNo)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
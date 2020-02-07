package com.gov.mpcb.reports.additional_info


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentAdditionalInfoBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.constants.Constants.Companion.ADDITIONAL_INFO_TEXT
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper.getReportFlagStatus
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper.getStringPreference
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper.setStringPreference
import com.gov.mpcb.utils.showMessage


class AdditionalInfoFragment :
    BaseFragmentReport<FragmentAdditionalInfoBinding, AdditionalInfoViewModel>(),
    AdditionalInfoNavigator {

    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_additional_info
    override fun getViewModel() = AdditionalInfoViewModel::class.java
    override fun getNavigator() = this@AdditionalInfoFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_18

        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_18)
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

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
//
        mBinding.edtAddInfo.addTextChangedListener{
            //Save data of additional info text in shared pref
            saveAdditionalInfoTextTemporary(mBinding.edtAddInfo.text.toString())
        }
    }

    private fun onSubmit() {
        report.data.routineReport.additionalInfo = mBinding.edtAddInfo.text.toString()

        //override additional info data in shared pref
        saveAdditionalInfoTextTemporary()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_18,
                reportStatus = true
            )

            //submit report only if all the reports are filled.
            if (checkIfReportsFilled()) {
                mViewModel.submitReport(
                    reportRequest = getReportData(visitReportId)
                )
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

    //When report is submitted successful, return to Visit Report page
    override fun onSubmitReportSuccess(msg: String) {
        showMessage(msg)
        activity!!.finish()
    }

    /**
     * Methods to save & retrieve data in shared pref temporarily
     */
    private fun saveAdditionalInfoTextTemporary(value: String = ""){
        setStringPreference(visitReportId + ADDITIONAL_INFO_TEXT, value)
    }

    private fun getAdditionalInfoTextTemporary(): String? =
        getStringPreference(visitReportId + ADDITIONAL_INFO_TEXT)


    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        reports = if (visitStatus) {    //If true, then retrieve temporary report data from Shared Pref
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if (reports != null) {
            mBinding.run {
                reports?.data?.routineReport?.run {
                    //Additional Info
                    if (!getAdditionalInfoTextTemporary().isNullOrEmpty()){
                        mBinding.edtAddInfo.setText(getAdditionalInfoTextTemporary())
                    }else {
                        edtAddInfo.setText(additionalInfo)
                    }

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
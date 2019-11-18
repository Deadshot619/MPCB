package com.example.mpcb.reports.industry

import android.app.DatePickerDialog
import android.widget.ArrayAdapter
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentIndustryCategoryBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isEmailValid
import com.example.mpcb.utils.validations.isValidMobile
import java.util.*

class IndustryReportFragment :
    BaseFragment<FragmentIndustryCategoryBinding, ReportsPageViewModel>(), ReportsPageNavigator {

    private val VISITED_ON = 1
    private val VALID_UPTO = 2
    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_industry_category
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@IndustryReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_1)
        setListener()

        val adapter = ArrayAdapter(
            getBaseActivity(),
            android.R.layout.simple_spinner_item,
            Constants.CATEGORY_LIST
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.catSpinner.adapter = adapter

        mBinding.edtVisitedIndustryOn.setOnClickListener { showDateDialog(VISITED_ON) }
        mBinding.edtValidUpto.setOnClickListener { showDateDialog(VALID_UPTO) }
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun showDateDialog(id: Int) {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    when (id) {
                        VISITED_ON -> mBinding.edtVisitedIndustryOn.setText("$year-${month + 1}-$dayOfMonth")
                        VALID_UPTO -> mBinding.edtValidUpto.setText("$year-${month + 1}-$dayOfMonth")
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    /**
     * Set listener on Radio Button
     */
    private fun setListener() {
        mBinding.rgConsent.setOnCheckedChangeListener { group, checkedId ->
            //Save Consent Obtained in Shared Pref
            report.data.routineReport.consentObtain = if (checkedId == R.id.rbConsentYes) 1 else 0
        }
    }

    private fun onSubmit() {
        report.data.industryCategoryReselect = "${mBinding.catSpinner.selectedItemPosition}"

        report.data.routineReport.run{
            visitedOn = mBinding.edtVisitedIndustryOn.text.toString()
            emailAddress = mBinding.visitCatEmailEd.text.toString()
            telephoneNumber = mBinding.visitCatTelephoneEd.text.toString()
            validityOfConsentUpto = mBinding.edtValidUpto.text.toString()
            validityOfConsentIe = mBinding.consentIeEd.text.toString()
            hwOfValidUptoDe = mBinding.consentDeEd.text.toString()
        }

        if (validateFieldsFilled()) {
            if (validateFieldsFilledCorrect()) {
                saveReportData(
                    reportKey = Constants.REPORT_1,
                    reportStatus = true
                )
                addReportFragment(Constants.REPORT_2)
            }
        }
    }

    /**
     * Method to check if the fields are correctly filled
     */
    private fun validateFieldsFilledCorrect(): Boolean {
        if(!isEmailValid(report.data.routineReport.emailAddress)){
            showMessage("Email Id is invalid")
            return false
        }
        if (!isValidMobile(report.data.routineReport.telephoneNumber)){
            showMessage("Invalid Number")
            return false
        }
        return true
    }

    /**
     * Method to validate if fields of report form are filled.
     */
    private fun validateFieldsFilled(): Boolean {
        if (report.data.industryCategoryReselect == "0") {
            showMessage("Select Category")
            return false
        }
        if (report.data.routineReport.visitedOn.isEmpty()) {
            showMessage("Enter Visited Industry On")
            return false
        }
        if (report.data.routineReport.emailAddress.isEmpty()) {
            showMessage("Enter Email Address of Unit")
            return false
        }
        if (report.data.routineReport.telephoneNumber.isEmpty()) {
            showMessage("Enter Telephone No of Unit")
            return false
        }
        if (report.data.routineReport.validityOfConsentUpto.isEmpty()) {
            showMessage("Enter Validity Upto")
            return false
        }
        if (report.data.routineReport.validityOfConsentIe.isEmpty()) {
            showMessage("Enter I.E(m3/day)")
            return false
        }
        if (report.data.routineReport.hwOfValidUptoDe.isEmpty()) {
            showMessage("Enter D.E(m3/day)")
            return false
        }
        if (report.data.routineReport.consentObtain == null) {
            showMessage("Select Consent Obtained")
            return false
        }

        return true
    }

    override fun onStart() {
        super.onStart()
        //set data to views in onStart
        setDataToViews()
//        Log.i("Industry", getReportData()?.data?.industryCategoryReselect)
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews(){
        reports = getReportData()
        //Spinner
        if(reports != null) {
            mBinding.run {
                catSpinner.setSelection(reports?.data?.industryCategoryReselect!!.toInt())
                edtVisitedIndustryOn.setText(reports?.data?.routineReport?.visitedOn)
                visitCatEmailEd.setText(reports?.data?.routineReport?.emailAddress)
                visitCatTelephoneEd.setText(reports?.data?.routineReport?.telephoneNumber)
                edtValidUpto.setText(reports?.data?.routineReport?.validityOfConsentUpto)
                consentIeEd.setText(reports?.data?.routineReport?.validityOfConsentIe)
                consentDeEd.setText(reports?.data?.routineReport?.hwOfValidUptoDe)
                if (reports?.data?.routineReport?.consentObtain == 1) {
                    rgConsent.check(R.id.rbConsentYes)
                } else if (reports?.data?.routineReport?.consentObtain == 0) {
                    rgConsent.check(R.id.rbConsentNo)
                }
            }
        }

    }
}
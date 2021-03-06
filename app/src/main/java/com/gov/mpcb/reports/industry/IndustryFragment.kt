package com.gov.mpcb.reports.industry

import android.app.DatePickerDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentIndustryCategoryBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.reports.ReportsPageNavigator
import com.gov.mpcb.reports.ReportsPageViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.constants.Constants.Companion.CATEGORY_LIST
import com.gov.mpcb.utils.constants.Constants.Companion.REPORT_1
import com.gov.mpcb.utils.constants.Constants.Companion.REPORT_2
import com.gov.mpcb.utils.constants.Constants.Companion.VISIT_REPORT_ID
import com.gov.mpcb.utils.parseToInt
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.utils.validations.isDecimal
import com.gov.mpcb.utils.validations.isEmailValid
import com.gov.mpcb.utils.validations.isValidMobile
import kotlinx.android.synthetic.main.button_save_next_layout.*
import java.util.*


class IndustryReportFragment :
    BaseFragmentReport<FragmentIndustryCategoryBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    private val VISITED_ON = 1
    private val VALID_UPTO = 2
    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_industry_category
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@IndustryReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_1

        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(
            btnSaveNextLayoutBinding = mBinding.btnSaveNext,
            showPreviousButton = false
        )

        (getBaseActivity() as ReportsPageActivity).setToolbar(REPORT_1)
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        val adapter = ArrayAdapter(
            getBaseActivity(),
            android.R.layout.simple_spinner_item,
            CATEGORY_LIST.values.toTypedArray()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.run {
            catSpinner.adapter = adapter

            edtVisitedIndustryOn.setOnClickListener { showDateDialog(VISITED_ON) }
            edtValidUpto.setOnClickListener { showDateDialog(VALID_UPTO) }
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
            }
        }
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
     * Set listeners
     */
    private fun setListener() {
        //Set listener on 'Industry Selected' spinner
        mBinding.catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //When item is selected, store the selected item data in 'report' for validation purposes
                report.data.industryCategoryReselect =
                    getSelectedSpinnerValueOfCategoryList(position)
            }

        }


        mBinding.rgConsent.setOnCheckedChangeListener { group, checkedId ->
            //Save Consent Obtained in Shared Pref
            report.data.routineReport.consentObtain = if (checkedId == R.id.rbConsentYes) {
                mBinding.edtValidUpto.isEnabled = true
                1
            } else {
                mBinding.edtValidUpto.run {
                    setText("")
                    isEnabled = false
                }
                0
            }
        }
    }

    private fun onSubmit() {
        report.data.industryCategoryReselect =
            getSelectedSpinnerValueOfCategoryList(mBinding.catSpinner.selectedItemPosition)

        report.data.routineReport.run {
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
                    reportNo = visitReportId,
                    reportKey = REPORT_1,
                    reportStatus = true
                )

                //Do fragment transaction to go to next Page
                addReportFragmentLocal(REPORT_2, visitReportId)
            }
        }
    }

    /**
     * Method to check if the fields are correctly filled
     */
    private fun validateFieldsFilledCorrect(): Boolean {


        //If industry category is selected as 'Closed'
        if (!isSelectedIndustryCategoryClosed(report)) {
            report.data.routineReport.run {
                if (!isEmailValid(emailAddress)) {
                    showMessage("Email Id is invalid")
                    return false
                }
                if (!isValidMobile(telephoneNumber)) {
                    showMessage("Invalid Telephone Number")
                    return false
                }
                if (!isDecimal(validityOfConsentIe)) {
                    showMessage("Invalid I.E. Number")
                    return false
                }
                if (!isDecimal(hwOfValidUptoDe)) {
                    showMessage("Invalid D.E. Number")
                    return false
                }
            }
        }
        return true
    }

    /**
     * Method to validate if fields of report form are filled.
     */
    private fun validateFieldsFilled(): Boolean {
//        if (report.data.industryCategoryReselect == "0") {
//            showMessage("Select Category")
//            return false
//        }

        report.data.routineReport.run {
            if (visitedOn.isEmpty()) {
                showMessage("Enter Visited Industry On")
                return false
            }

            //If industry category is selected as 'Closed'
            if (!isSelectedIndustryCategoryClosed(report)) {
                if (emailAddress.isEmpty()) {
                    showMessage("Enter Email Address of Unit")
                    return false
                }
                if (telephoneNumber.isEmpty()) {
                    showMessage("Enter Telephone No of Unit")
                    return false
                }
                if (validityOfConsentIe.isEmpty()) {
                    showMessage("Enter I.E(m3/day)")
                    return false
                }
                if (hwOfValidUptoDe.isEmpty()) {
                    showMessage("Enter D.E(m3/day)")
                    return false
                }

                if (consentObtain == 1 || consentObtain == 0) {
                    //If Consent Obtained is 'Yes' then check if 'Valid upto' is filled
                    if (consentObtain == 1) {
                        if (validityOfConsentUpto.isEmpty()) {
                            showMessage("Enter Validity Upto")
                            return false
                        }
                    }
                } else {
                    showMessage("Select Consent Obtained")
                    return false
                }
            }
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
    override fun setDataToViews() {
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        //Spinner
        if (reports != null) {
            mBinding.run {
                if (reports?.data?.industryCategoryReselect != "")
                    reports?.data?.industryCategoryReselect?.let { value ->
                        catSpinner.setSelection(
                            if (value.parseToInt() > 2)
                                value.parseToInt() - 4
                            else
                                value.parseToInt()
                        )
                    }
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

    /**
     * This method is used to get selected spinner item value from [CATEGORY_LIST].
     *
     * @param selectedSpinnerItemPosition Takes Selected spinner item position as argument
     */
    private fun getSelectedSpinnerValueOfCategoryList(selectedSpinnerItemPosition: Int): String{
        return try {
            CATEGORY_LIST.filterValues {
                it == CATEGORY_LIST[
                        if (selectedSpinnerItemPosition > 1)
                            selectedSpinnerItemPosition + 4
                        else
                            selectedSpinnerItemPosition
                ]
            }.keys.first().toString()
        } catch (e: Exception) {
            "0"
        }
    }
}
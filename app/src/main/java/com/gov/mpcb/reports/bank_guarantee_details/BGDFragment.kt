package com.gov.mpcb.reports.bank_guarantee_details

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentBankGuaranteeBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class BGDFragment : BaseFragmentReport<FragmentBankGuaranteeBinding, BGDViewModel>(), BGDNavigator {

    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_bank_guarantee
    override fun getViewModel() = BGDViewModel::class.java
    override fun getNavigator() = this@BGDFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_17

        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_17)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()
        setListener()

        mBinding.run {
            tvAddMore.setOnClickListener { mViewModel.addItem() }
            imgDelete.setOnClickListener { mViewModel.deleteItem() }
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
            }
        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvBank.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = BGDAdapter(getBaseActivity(), mViewModel, visitStatus)
        mBinding.rvBank.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    //Set Listeners to Views
    private fun setListener() {
        mBinding.rgBGImposed.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.bgImposed = if (checkedId == R.id.rbBGYes) {
                //If yes, then make below fields visible
                mBinding.run {
                    txtBgImposedAgainst.visibility = View.VISIBLE
                    rgBGImposedAgainst.visibility = View.VISIBLE
                    txtNumber.visibility = View.VISIBLE
                }

                "1"
            } else {
                //If no, then make below fields invisible, also clear the selection & text of the fields
                mBinding.run {
                    txtBgImposedAgainst.visibility = View.GONE
                    rgBGImposedAgainst.visibility = View.GONE
                    txtNumber.visibility = View.GONE
                }

                mBinding.rgBGImposedAgainst.clearCheck()
                mBinding.edtNumber.setText("")

                "0"
            }
        }

        mBinding.rgBGImposedAgainst.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.bgImposedAgainst =
                when (checkedId) {
                    R.id.rbBGAgainstYes -> "0"
                    R.id.rbBGAgainstNo -> "1"
                    else -> ""
                }
        }
    }

    private fun onSubmit() {

        report.data.routineReport.bgImposedNumber = mBinding.edtNumber.text.toString()

        report.data.routineReportBankDetails = mViewModel.getSourceList().value!!

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_17,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_18, visitReportId)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
//If industry category is selected as 'Closed'
        if (!isSelectedIndustryCategoryClosed(report)) {

//      BG Imposed
            if (report.data.routineReport.bgImposed.isNullOrEmpty()) {
                showMessage("Select BG Imposed")
                return false
            } else if (report.data.routineReport.bgImposed == "1") { //Check below fields only if BG imposed is selected as 'YES'
//          BG Imposed Against
                if (report.data.routineReport.bgImposedAgainst.isNullOrEmpty()) {
                    showMessage("Select BG Imposed Against")
                    return false
                }

//          BG Number
                if (report.data.routineReport.bgImposedNumber.isNullOrEmpty()) {
                    showMessage("Enter BG Imposed Number")
                    return false
                }
            }


//        else if (!isDecimal(report.data.routineReport.bgImposedNumber)){
//            showMessage("Invalid BG Imposed Number")
//            return false
//        }


            val sourceList = mViewModel.getSourceList().value!!
            for (item in sourceList) {
                if (item.bankGuaranteeImposedFor.isEmpty()) {
                    showMessage("Enter Bank Guarantee Imposed For")
                    isValid = false
                    break
                }
                if (item.bankSubmitted.isEmpty()) {
                    showMessage("Select BG Submitted")
                    isValid = false
                    break
                } else if (item.bankSubmitted == "0") {
                    if (item.bankGuarentedNo.isEmpty()) {
                        showMessage("Enter Bank Guaranted No")
                        isValid = false
                        break
                    }
                    if (item.dateOfGuarantee.isEmpty()) {
                        showMessage("Enter Date of Guarantee")
                        isValid = false
                        break
                    }
                    if (item.dateOfValidity.isEmpty()) {
                        showMessage("Enter Date Of Validity")
                        isValid = false
                        break
                    }
                }

            }
        }
        return isValid
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
                    //                    BG Imposed
                    if (bgImposed == "1")
                        rgBGImposed.check(R.id.rbBGYes)
                    else
                        rgBGImposed.check(R.id.rbBGNo)

//                    BG Imposed Against
                    if (bgImposedAgainst == "0")
                        rgBGImposedAgainst.check(R.id.rbBGAgainstYes)
                    else if (bgImposedAgainst == "1")
                        rgBGImposedAgainst.check(R.id.rbBGAgainstNo)

//                    BG Number
                    edtNumber.setText(bgImposedNumber)

                }
            }
        }

//        BG Recyler View
        if (reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportBankDetails)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
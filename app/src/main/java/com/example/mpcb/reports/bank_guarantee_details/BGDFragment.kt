package com.example.mpcb.reports.bank_guarantee_details

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentBankGuaranteeBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal

class BGDFragment : BaseFragment<FragmentBankGuaranteeBinding, BGDViewModel>(), BGDNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_bank_guarantee
    override fun getViewModel() = BGDViewModel::class.java
    override fun getNavigator() = this@BGDFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_17)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage(visitReportId)
        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()
        setListener()

        mBinding.run{
            tvAddMore.setOnClickListener { mViewModel.addItem() }
            imgDelete.setOnClickListener { mViewModel.deleteItem() }
            btnSaveNext.btnSubmit.setOnClickListener { onSubmit() }
        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvBank.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = BGDAdapter(getBaseActivity(), mViewModel, visitStatus)
        mBinding.rvBank.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun setListener() {
        mBinding.rgBGImposed.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.bgImposed = if (checkedId == R.id.rbBGYes) "1" else "0"
            report.data.routineReport.bgImposedAgainst =
                if (checkedId == R.id.rbBGAgainstYes) "1" else "0"
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
            )//Put the Visit Report ID in bundle to share to Fragments
            val bundle = Bundle()
            bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)
            addReportFragment(Constants.REPORT_18, bundle)
        }
    }

    private fun validate(): Boolean {
        var isValid = true

//        BG Imposed
        if (report.data.routineReport.bgImposed.isNullOrEmpty()) {
            showMessage("Select BG Imposed")
            return false
        }

//        BG Imposed Against
        if (report.data.routineReport.bgImposedAgainst.isNullOrEmpty()) {
            showMessage("Select BG Imposed Against")
            return false
        }

//        BG Number
        if (report.data.routineReport.bgImposedNumber.isNullOrEmpty()) {
            showMessage("Enter BG Imposed Number")
            return false
        }else if (!isDecimal(report.data.routineReport.bgImposedNumber!!)){
            showMessage("Invalid BG Imposed Number")
            return false
        }


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
            }
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
        return isValid
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        reports = getReportData(visitReportId)

        if (reports != null){
            mBinding.run {
                reports?.data?.routineReport?.run{
//                    BG Imposed
                    if (bgImposed == "1")
                        rgBGImposed.check(R.id.rbBGYes)
                    else
                        rgBGImposed.check(R.id.rbBGNo)

//                    BG Imposed Against
                    if (bgImposedAgainst == "1")
                        rgBGImposedAgainst.check(R.id.rbBGAgainstYes)
                    else
                        rgBGImposedAgainst.check(R.id.rbBGAgainstNo)

//                    BG Number
                    edtNumber.setText(bgImposedNumber)

                }
            }
        }

//        BG Recyler View
        if(reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportBankDetails)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
package com.example.mpcb.reports.hazardous_waste_management

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentHazardiousBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal

class HazardousFragment : BaseFragment<FragmentHazardiousBinding, HazardousViewModel>(),
    HazardousNavigator {

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_hazardious
    override fun getViewModel() = HazardousViewModel::class.java
    override fun getNavigator() = this@HazardousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_12)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage(visitReportId)

        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.imgDelete.setOnClickListener { mViewModel.deleteItem() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvHazardousReports.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = HazardousAdapter(getBaseActivity(), mViewModel)
        mBinding.rvHazardousReports.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun onSubmit() {
        report.data.routineReportHazardousWaste = mViewModel.getSourceList().value!!

//        addReportFragment(Constants.REPORT_13)
        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_12,
                reportStatus = true
            )//Put the Visit Report ID in bundle to share to Fragments
            val bundle = Bundle()
            bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)
            addReportFragment(Constants.REPORT_13, bundle)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val sourceList = mViewModel.getSourceList().value!!
        for (item in sourceList) {
//            Category
            if (item.hazardousWasteCategoryName.isNullOrEmpty()) {
                showMessage("Enter Hazardous Waste Category Name")
                isValid = false
                break
            }

//            Quantity as per consent
            if (item.hazardousWasteQuantityString.isNullOrEmpty()) {
                showMessage("Enter Quantity As per Consent")
                isValid = false
                break
            }else if (!isDecimal(item.hazardousWasteQuantityString!!)){
                showMessage("Invalid Quantity As per Consent value")
                isValid = false
                break
            }

//            Method of disposal as per consent
            if (item.hwDisposalMethodString.isNullOrEmpty()) {
                showMessage("Enter Disposal Method")
                isValid = false
                break
            }

//            Actual Disposal
            if (item.hwActualDisposalString.isNullOrEmpty()) {
                showMessage("Enter Actual Disposal")
                isValid = false
                break
            }else if (!isDecimal(item.hwActualDisposalString!!)){
                showMessage("Inavlid Actual Disposal value")
                isValid = false
                break
            }

//            Quantity Disposal form 4
            if (item.hwFormDisposalString.isNullOrEmpty()) {
                showMessage("Enter Quantity Disposal")
                isValid = false
                break
            }else if (!isDecimal(item.hwFormDisposalString!!)){
                showMessage("Invalid Quantity Disposal value")
                isValid = false
                break
            }

//            Quantity Disposal form CSWTSDS
            if (item.hwFormCswtsdfString.isNullOrEmpty()) {
                showMessage("Enter Quantity disposal at CSWTSDS")
                isValid = false
                break
            }else if (!isDecimal(item.hwFormCswtsdfString!!)){
                showMessage("Invalid Quantity disposal at CSWTSDS value")
                isValid = false
                break
            }

//            Quantity Disposal form co-processing
            if (item.hwFormCoProcessingString.isNullOrEmpty()) {
                showMessage("Enter Quantity disposal for co-processing")
                isValid = false
                break
            }else if (!isDecimal(item.hwFormCoProcessingString!!)){
                showMessage("Invalid Quantity disposal for co-processing value")
                isValid = false
                break
            }

//            Quantity Disposed by actual user
            if (item.hwDisposedActualuserString.isNullOrEmpty()) {
                showMessage("Enter Quantity disposed by actual user")
                isValid = false
                break
            }else if (!isDecimal(item.hwDisposedActualuserString!!)){
                showMessage("Invalid Quantity disposed by actual user value")
                isValid = false
                break
            }

//            Last disposal quantity
            if (item.hwDisposalQuantityString.isNullOrEmpty()) {
                showMessage("Enter Last disposal Quantity")
                isValid = false
                break
            }else if (!isDecimal(item.hwDisposalQuantityString!!)){
                showMessage("Invalid Last disposal Quantity value")
                isValid = false
                break
            }

//            Last Disposal Date
            if (item.hwDisposalDate.isNullOrEmpty()) {
                showMessage("Enter Last disposal date")
                isValid = false
                break
            }

//            UOM
            if (item.hwDisposalQuantityUnit == "0") {
                showMessage("Select UOM")
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
        if(reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportHazardousWaste)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
package com.gov.mpcb.reports.hazardous_waste_management

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentHazardiousBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.utils.validations.isDecimal

class HazardousFragment : BaseFragmentReport<FragmentHazardiousBinding, HazardousViewModel>(),
    HazardousNavigator {

    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_hazardious
    override fun getViewModel() = HazardousViewModel::class.java
    override fun getNavigator() = this@HazardousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set currectReportNumber
        currentReportNumber = Constants.REPORT_12

        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_12)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()

        mBinding.run {
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
            }
            tvAddMore.setOnClickListener { mViewModel.addItem() }
            imgDelete.setOnClickListener { mViewModel.deleteItem() }
        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvHazardousReports.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = HazardousAdapter(getBaseActivity(), mViewModel, visitStatus)
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
            addReportFragmentLocal(Constants.REPORT_13, visitReportId)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        //If industry category is selected as 'Closed'
        if (!isSelectedIndustryCategoryClosed(report)) {

            val sourceList = mViewModel.getSourceList().value!!
            for (item in sourceList) {
//            Category
                if (item.hazardousWasteCategoryName.isNullOrEmpty()) {
                    showMessage("Enter Hazardous Waste Category Name")
                    isValid = false
                    break
                }

//            Quantity as per consent
                if (item.hazardousWasteQuantityString.toString().isNullOrEmpty()) {
                    showMessage("Enter Quantity As per Consent")
                    isValid = false
                    break
                } else if (!isDecimal(item.hazardousWasteQuantityString!!)) {
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
                if (item.hwActualDisposalString.toString().isNullOrEmpty()) {
                    showMessage("Enter Actual Disposal")
                    isValid = false
                    break
                } else if (!isDecimal(item.hwActualDisposalString!!)) {
                    showMessage("Inavlid Actual Disposal value")
                    isValid = false
                    break
                }

//            Quantity Disposal form 4
                if (item.hwFormDisposalString.toString().isNullOrEmpty()) {
                    showMessage("Enter Quantity Disposal")
                    isValid = false
                    break
                } else if (!isDecimal(item.hwFormDisposalString!!)) {
                    showMessage("Invalid Quantity Disposal value")
                    isValid = false
                    break
                }

//            Quantity Disposal form CSWTSDS
                if (item.hwFormCswtsdfString.toString().isNullOrEmpty()) {
                    showMessage("Enter Quantity disposal at CSWTSDS")
                    isValid = false
                    break
                } else if (!isDecimal(item.hwFormCswtsdfString!!)) {
                    showMessage("Invalid Quantity disposal at CSWTSDS value")
                    isValid = false
                    break
                }

//            Quantity Disposal form co-processing
                if (item.hwFormCoProcessingString.toString().isNullOrEmpty()) {
                    showMessage("Enter Quantity disposal for co-processing")
                    isValid = false
                    break
                } else if (!isDecimal(item.hwFormCoProcessingString!!)) {
                    showMessage("Invalid Quantity disposal for co-processing value")
                    isValid = false
                    break
                }

//            Quantity Disposed by actual user
                if (item.hwDisposedActualuserString.toString().isNullOrEmpty()) {
                    showMessage("Enter Quantity disposed by actual user")
                    isValid = false
                    break
                } else if (!isDecimal(item.hwDisposedActualuserString!!)) {
                    showMessage("Invalid Quantity disposed by actual user value")
                    isValid = false
                    break
                }

//            Last disposal quantity
                if (item.hwDisposalQuantityString.toString().isNullOrEmpty()) {
                    showMessage("Enter Last disposal Quantity")
                    isValid = false
                    break
                } else if (!isDecimal(item.hwDisposalQuantityString!!)) {
                    showMessage("Invalid Last disposal Quantity value")
                    isValid = false
                    break
                }

//            Last Disposal Date
//            if (item.hwDisposalDate.isNullOrEmpty()) {
//                showMessage("Enter Last disposal date")
//                isValid = false
//                break
//            }

//            UOM
                if (item.hwDisposalQuantityUnit == "0") {
                    showMessage("Select UOM")
                    isValid = false
                    break
                }
            }
        }
        return isValid
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        //If visit status is Visited, then show the data retrieved from Api
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if (reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportHazardousWaste)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
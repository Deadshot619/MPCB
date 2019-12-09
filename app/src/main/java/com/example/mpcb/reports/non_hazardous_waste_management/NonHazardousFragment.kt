package com.example.mpcb.reports.non_hazardous_waste_management

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentNonHazardiousBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal

class NonHazardousFragment : BaseFragment<FragmentNonHazardiousBinding, NonHazardousViewModel>(),
    NonHazardousNavigator {
    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_non_hazardious
    override fun getViewModel() = NonHazardousViewModel::class.java
    override fun getNavigator() = this@NonHazardousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_13)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()

        mBinding.run{
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
                btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_14, visitReportId) }
            }
            tvAddMore.setOnClickListener { mViewModel.addItem() }
            imgDelete.setOnClickListener { mViewModel.deleteItem() }
        }

    }

    private fun setUpRecyclerView() {
        mBinding.rvNonHazardous.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = NonHazardousAdapter(getBaseActivity(), mViewModel, visitStatus)
        mBinding.rvNonHazardous.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun onSubmit() {
        report.data.routineReportNonHazardousWaste = mViewModel.getSourceList().value!!

//        addReportFragment(Constants.REPORT_14)
        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_13,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_14, visitReportId)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val sourceList = mViewModel.getSourceList().value!!
        for (item in sourceList) {
//            Waste NAme
            if (item.nhwWasteName.isNullOrEmpty()) {
                showMessage("Enter Waste Name")
                isValid = false
                break
            }

//            Quantity as per consent
            if (item.nhwQuantityString.isNullOrEmpty()) {
                showMessage("Enter Quantity As per Consent")
                isValid = false
                break
            }else if (!isDecimal(item.nhwQuantityString!!)){
                showMessage("Invalid Quantity As per Consent value")
                isValid = false
                break
            }

//            Method of disposal as per consent
            if (item.nhwDisposalMethod.isNullOrEmpty()) {
                showMessage("Enter Disposal Method")
                isValid = false
                break
            }

//            Last Disposal Date
            if (item.nhwDisposalDate.isNullOrEmpty()) {
                showMessage("Enter Last Disposal Date")
                isValid = false
                break
            }

//            Last Disposal Quantity
            if (item.nhwDisposalQuantityString.isNullOrEmpty()) {
                showMessage("Enter Last Disposal Quantity")
                isValid = false
                break
            }else if (!isDecimal(item.nhwDisposalQuantityString!!)){
                showMessage("Invalid Last Disposal Quantity value")
                isValid = false
                break
            }

//            Actual Disposal
            if (item.nhwActualdisposalString.isNullOrEmpty()) {
                showMessage("Enter Actual Disposal")
                isValid = false
                break
            }else if (!isDecimal(item.nhwActualdisposalString!!)){
                showMessage("Invalid Actual Disposal value")
                isValid = false
                break
            }

//            Uom
            if (item.nhwDisposalQuantityUnit == "0") {
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
        //If visit status is Visited, then show the data retrieved from Api
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if(reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportNonHazardousWaste)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
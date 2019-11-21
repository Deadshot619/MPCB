package com.example.mpcb.reports.hazardous_waste_management

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentHazardiousBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

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
        setUpRecyclerView()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
//        showMessage(visitReportId)

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

        addReportFragment(Constants.REPORT_13)
        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_12,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_13)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val sourceList = mViewModel.getSourceList().value!!
        for (item in sourceList) {
            if (item.hazardousWasteCategoryName.isNullOrEmpty()) {
                showMessage("Enter Hazardous Waste Category Name")
                isValid = false
                break
            }
            if (item.hazardousWasteQuantityString.isNullOrEmpty()) {
                showMessage("Enter Quantity As per Consent")
                isValid = false
                break
            }
            if (item.hwDisposalMethodString.isNullOrEmpty()) {
                showMessage("Enter Disposal Method")
                isValid = false
                break
            }
            if (item.hwActualDisposalString.isNullOrEmpty()) {
                showMessage("Enter Actual Disposal")
                isValid = false
                break
            }
            if (item.hwFormDisposalString.isNullOrEmpty()) {
                showMessage("Enter Quantity Disposal")
                isValid = false
                break
            }
            if (item.hwFormCswtsdfString.isNullOrEmpty()) {
                showMessage("Enter Quantity disposal at CSWTSDS")
                isValid = false
                break
            }
            if (item.hwFormCoProcessingString.isNullOrEmpty()) {
                showMessage("Enter Quantity disposal for co-processing")
                isValid = false
                break
            }
            if (item.hwDisposedActualuserString.isNullOrEmpty()) {
                showMessage("Enter Quantity disposed by actual user")
                isValid = false
                break
            }
            if (item.hwDisposalQuantityString.isNullOrEmpty()) {
                showMessage("Enter Last disposal Quantity")
                isValid = false
                break
            }
            if (item.hwDisposalDate.isNullOrEmpty()) {
                showMessage("Enter Last disposal date")
                isValid = false
                break
            }
            if (item.hwDisposalQuantityUnit == 0) {
                showMessage("Select UOM")
                isValid = false
                break
            }
        }

        return isValid
    }
}
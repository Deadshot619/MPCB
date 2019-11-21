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
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_13)
        setUpRecyclerView()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
//        showMessage(visitReportId)

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.imgDelete.setOnClickListener { mViewModel.deleteItem() }

    }

    private fun setUpRecyclerView() {
        mBinding.rvNonHazardous.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = NonHazardousAdapter(getBaseActivity(), mViewModel)
        mBinding.rvNonHazardous.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun onSubmit() {
        report.data.routineReportNonHazardousWaste = mViewModel.getSourceList().value!!

        addReportFragment(Constants.REPORT_14)
        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_13,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_14)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val sourceList = mViewModel.getSourceList().value!!
        for (item in sourceList) {
            if (item.nhwWasteName.isNullOrEmpty()) {
                showMessage("Enter Waste Name")
                isValid = false
                break
            }
            if (item.nhwQuantityString.isNullOrEmpty()) {
                showMessage("Enter Quantity As per Consent")
                isValid = false
                break
            }
            if (item.nhwDisposalMethod.isNullOrEmpty()) {
                showMessage("Enter Disposal Method")
                isValid = false
                break
            }
            if (item.nhwDisposalDate.isNullOrEmpty()) {
                showMessage("Enter Last Disposal Date")
                isValid = false
                break
            }
            if (item.nhwDisposalQuantityString.isNullOrEmpty()) {
                showMessage("Enter Last Disposal Quantity")
                isValid = false
                break
            }
            if (item.nhwActualdisposalString.isNullOrEmpty()) {
                showMessage("Enter Actual Disposal")
                isValid = false
                break
            }
            if (item.nhwDisposalQuantityUnit == 0) {
                showMessage("Select UOM")
                isValid = false
                break
            }
        }

        return isValid
    }
}
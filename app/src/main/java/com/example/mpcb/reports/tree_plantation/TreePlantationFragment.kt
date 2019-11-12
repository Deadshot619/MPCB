package com.example.mpcb.reports.tree_plantation


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTreePlantationBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage


class TreePlantationFragment : BaseFragment<FragmentTreePlantationBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_tree_plantation
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreePlantationFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_14)


        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun onSubmit() {
        report.data.routineReport.treePlantationPlotArea = mBinding.edtTotalPlot.text.toString()
        report.data.routineReport.treePlantationBuiltArea = mBinding.edtBuiltArea.text.toString()
        report.data.routineReport.treePlantationGreenBeltArea =
            mBinding.edtGreenBelt.text.toString()
        report.data.routineReport.treePlantationPlantationNo =
            mBinding.edtPlantationDone.text.toString()
        report.data.routineReport.treePlantationProposedPlantation =
            mBinding.edtProposedPlantation.text.toString()

        if (validate()) {
            saveReportData(
                reportKey = Constants.REPORT_14,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_15)
        }
    }

    private fun validate(): Boolean {
        if (report.data.routineReport.treePlantationPlotArea.isNullOrEmpty()) {
            showMessage("Enter Total Plot Area in sq.mt")
            return false
        }
        if (report.data.routineReport.treePlantationBuiltArea.isNullOrEmpty()) {
            showMessage("Enter Built Up Area in sq.mt")
            return false
        }
        if (report.data.routineReport.treePlantationGreenBeltArea.isNullOrEmpty()) {
            showMessage("Enter Green Belt Area in sq.mt")
            return false
        }
        if (report.data.routineReport.treePlantationPlantationNo.isNullOrEmpty()) {
            showMessage("Enter Plantation done in No")
            return false
        }
        if (report.data.routineReport.treePlantationProposedPlantation.isNullOrEmpty()) {
            showMessage("Enter Proposed Plantation")
            return false
        }

        return true
    }

}

package com.example.mpcb.reports.disposal

import android.widget.CompoundButton
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDisposalBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.parseToInt
import com.example.mpcb.utils.showMessage

class DisposalFragment : BaseFragment<FragmentDisposalBinding, ReportsPageViewModel>(),
    ReportsPageNavigator,
    CompoundButton.OnCheckedChangeListener {

    override fun getLayoutId() = R.layout.fragment_disposal
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@DisposalFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_5)

        setListener()
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun setListener() {
        mBinding.cbIndusCETP.setOnCheckedChangeListener(this)
        mBinding.cbIndusLandGardening.setOnCheckedChangeListener(this)
        mBinding.cbIndusRecycle.setOnCheckedChangeListener(this)
        mBinding.cbIndusSewageTreatment.setOnCheckedChangeListener(this)
        mBinding.cbIndusAnyOther.setOnCheckedChangeListener(this)

        mBinding.cbDomesticCETP.setOnCheckedChangeListener(this)
        mBinding.cbDomesticLandGardening.setOnCheckedChangeListener(this)
        mBinding.cbDomesticRecycle.setOnCheckedChangeListener(this)
        mBinding.cbDomesticSewageTreatment.setOnCheckedChangeListener(this)
        mBinding.cbDomesticAnyOther.setOnCheckedChangeListener(this)

        mBinding.rgDisposalConsent.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.disposalIndustrialAsPerConsent =
                if (checkedId == R.id.rbDisposalYes) "1" else "0"
        }

        mBinding.rgOperationMaintenance.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.operationAndMaintainanceInsus = when (checkedId) {
                R.id.rbPoor -> "0"
                R.id.rbAverage -> "1"
                R.id.rbGood -> "2"
                else -> ""
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.cbIndusCETP -> report.data.routineReport.disposalIndustrialCETP =
                if (isChecked) 1 else 0
            R.id.cbIndusLandGardening -> report.data.routineReport.disposalIndustrialLandGardening =
                if (isChecked) 1 else 0
            R.id.cbIndusRecycle -> report.data.routineReport.disposalIndustrialRecycle =
                if (isChecked) 1 else 0
            R.id.cbIndusSewageTreatment -> report.data.routineReport.disposalIndustrialLocalBodySewage =
                if (isChecked) 1 else 0
            R.id.cbIndusAnyOther -> report.data.routineReport.disposalIndustrialAnyOther =
                if (isChecked) 1 else 0

            R.id.cbDomesticCETP -> report.data.routineReport.disposalDomesticCETP =
                if (isChecked) 1 else 0
            R.id.cbDomesticLandGardening -> report.data.routineReport.disposalDomesticLandGardening =
                if (isChecked) 1 else 0
            R.id.cbDomesticRecycle -> report.data.routineReport.disposalDomesticRecycle =
                if (isChecked) 1 else 0
            R.id.cbDomesticSewageTreatment -> report.data.routineReport.disposalDomesticLocalBodySewage =
                if (isChecked) 1 else 0
            R.id.cbDomesticAnyOther -> report.data.routineReport.disposalDomesticAnyOther =
                if (isChecked) 1 else 0
        }
    }

    private fun onSubmit() {
        report.data.routineReport.disposalIndustrialCETPText = mBinding.edIndusCETP.text.toString()
        report.data.routineReport.disposalIndustrialLandGardeningText =
            mBinding.edIndusLandGardening.text.toString()
        report.data.routineReport.disposalIndustrialRecycleText =
            mBinding.edIndusRecycle.text.toString()
        report.data.routineReport.disposalIndustrialLocalBodySewageText =
            mBinding.edIndusSewageTreatment.text.toString()
        report.data.routineReport.disposalIndustrialAnyOtherTextRemarks =
            mBinding.edIndusExtraName.text.toString()
        report.data.routineReport.disposalIndustrialAnyOtherText =
            mBinding.edIndusExtraNameValue.text.toString()
        report.data.routineReport.disposalIndustrialTotal =
            mBinding.edIndustrialTotal.text.toString().parseToInt()


        report.data.routineReport.disposalDomesticCETPText = mBinding.edDomesticCETP.text.toString()
        report.data.routineReport.disposalDomesticLandGardeningText =
            mBinding.edDomesticLandGardening.text.toString()
        report.data.routineReport.disposalDomesticRecycleText =
            mBinding.edDomesticRecycle.text.toString()
        report.data.routineReport.disposalDomesticLocalBodySewageText =
            mBinding.edDomesticSewageTreatment.text.toString()
        report.data.routineReport.disposalDomesticAnyOtherTextRemarks =
            mBinding.edDomesticExtraName.text.toString()
        report.data.routineReport.disposalDomesticAnyOtherText =
            mBinding.edDomesticExtraNameValue.text.toString()
        report.data.routineReport.disposalDomesticTotal =
            mBinding.edDomesticTotal.text.toString().parseToInt()

        report.data.routineReport.disposalObservation = mBinding.edRemark.text.toString()

        saveReportData()
        addReportFragment(Constants.REPORT_6)
    }
}
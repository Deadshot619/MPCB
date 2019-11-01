package com.example.mpcb.reports.treatment


import android.view.View
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTreatmentBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage


class TreatmentFragment : BaseFragment<FragmentTreatmentBinding, ReportsPageViewModel>(),
    ReportsPageNavigator,
    CompoundButton.OnCheckedChangeListener {

    override fun getLayoutId() = R.layout.fragment_treatment
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreatmentFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_3)

        setListeners()
        setCheckBoxListener()
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun setListeners() {
        mBinding.imgPrimaryExpandCollapse.setOnClickListener {
            if (mBinding.layLinPrimaryChild.visibility == View.VISIBLE) {
                mBinding.layLinPrimaryChild.visibility = View.GONE
                mBinding.imgPrimaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialPrimary = 0
            } else {
                mBinding.layLinPrimaryChild.visibility = View.VISIBLE
                mBinding.imgPrimaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialPrimary = 1
            }
        }

        mBinding.imgSecondaryExpandCollapse.setOnClickListener {
            if (mBinding.layLinSecondaryChild.visibility == View.VISIBLE) {
                mBinding.layLinSecondaryChild.visibility = View.GONE
                mBinding.imgSecondaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialSecondary = 0
            } else {
                mBinding.layLinSecondaryChild.visibility = View.VISIBLE
                mBinding.imgSecondaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialSecondary = 1
            }
        }

        mBinding.imgTertiaryExpandCollapse.setOnClickListener {
            if (mBinding.layLinTertiaryChild.visibility == View.VISIBLE) {
                mBinding.layLinTertiaryChild.visibility = View.GONE
                mBinding.imgTertiaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialTertiary = 0
            } else {
                mBinding.layLinTertiaryChild.visibility = View.VISIBLE
                mBinding.imgTertiaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialTertiary = 1
            }
        }

        mBinding.imgAdvanceExpandCollapse.setOnClickListener {
            if (mBinding.layLinAdvanceChild.visibility == View.VISIBLE) {
                mBinding.layLinAdvanceChild.visibility = View.GONE
                mBinding.imgAdvanceExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialAdvanced = 0
            } else {
                mBinding.layLinAdvanceChild.visibility = View.VISIBLE
                mBinding.imgAdvanceExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
                report.data.routineReport.treatmentIndustrialTertiary = 1
            }
        }

        mBinding.rgETP.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.etpOperational = if (checkedId == R.id.rbETPYes) 1 else 0
        }

        mBinding.rgSTP.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.stpOperational = if (checkedId == R.id.rbSTPYes) 1 else 0
        }
    }

    private fun setCheckBoxListener() {
        mBinding.cbPrimaryOG.setOnCheckedChangeListener(this)
        mBinding.cbPrimaryScreening.setOnCheckedChangeListener(this)
        mBinding.cbPrimaryNeutralization.setOnCheckedChangeListener(this)
        mBinding.cbPrimarySetting.setOnCheckedChangeListener(this)
        mBinding.cbPrimaryAnyOther.setOnCheckedChangeListener(this)

        mBinding.cbSecondaryActivated.setOnCheckedChangeListener(this)
        mBinding.cbSecondaryMBBR.setOnCheckedChangeListener(this)
        mBinding.cbSecondarySBR.setOnCheckedChangeListener(this)
        mBinding.cbSecondaryTrickling.setOnCheckedChangeListener(this)
        mBinding.cbSecondaryAnyOther.setOnCheckedChangeListener(this)

        mBinding.cbTertiaryPress.setOnCheckedChangeListener(this)
        mBinding.cbTertiaryActivated.setOnCheckedChangeListener(this)
        mBinding.cbTertiaryDual.setOnCheckedChangeListener(this)
        mBinding.cbTertiaryAnyOther.setOnCheckedChangeListener(this)

        mBinding.cbAdvanceReverse.setOnCheckedChangeListener(this)
        mBinding.cbAdvanceMEE.setOnCheckedChangeListener(this)
        mBinding.cbAdvanceUltra.setOnCheckedChangeListener(this)
        mBinding.cbAdvanceNano.setOnCheckedChangeListener(this)
        mBinding.cbAdvanceATFD.setOnCheckedChangeListener(this)
        mBinding.cbAdvanceAnyOther.setOnCheckedChangeListener(this)

        mBinding.cbDomesticSepticTank.setOnCheckedChangeListener(this)
        mBinding.cbDomesticSewage.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.cbPrimaryOG -> report.data.routineReport.industrialPrimaryOGTrap =
                if (isChecked) 1 else 0
            R.id.cbPrimaryScreening -> report.data.routineReport.industrialPrimaryScreening =
                if (isChecked) 1 else 0
            R.id.cbPrimaryNeutralization -> report.data.routineReport.industrialPrimaryNeutralization =
                if (isChecked) 1 else 0
            R.id.cbPrimarySetting -> report.data.routineReport.industrialPrimaryPrimarySettling =
                if (isChecked) 1 else 0
            R.id.cbPrimaryAnyOther -> report.data.routineReport.industrialPrimaryAnyOther =
                if (isChecked) 1 else 0

            R.id.cbSecondaryActivated -> report.data.routineReport.industrialSecondaryActivatedSludgeProcess =
                if (isChecked) 1 else 0
            R.id.cbSecondaryMBBR -> report.data.routineReport.industrialSecondaryMbbr =
                if (isChecked) 1 else 0
            R.id.cbSecondarySBR -> report.data.routineReport.industrialSecondarySbr =
                if (isChecked) 1 else 0
            R.id.cbSecondaryTrickling -> report.data.routineReport.industrialSecondaryTricklingFilter =
                if (isChecked) 1 else 0
            R.id.cbSecondaryAnyOther -> report.data.routineReport.industrialSecondaryAnyOther =
                if (isChecked) 1 else 0

            R.id.cbTertiaryPress -> report.data.routineReport.industrialTertiaryPresserSandFilter =
                if (isChecked) 1 else 0
            R.id.cbTertiaryActivated -> report.data.routineReport.industrialTertiaryActivatedCarbonFilter =
                if (isChecked) 1 else 0
            R.id.cbTertiaryDual -> report.data.routineReport.industrialTertiaryDualMediaFilter =
                if (isChecked) 1 else 0
            R.id.cbTertiaryAnyOther -> report.data.routineReport.industrialTertiaryAnyOther =
                if (isChecked) 1 else 0

            R.id.cbAdvanceReverse -> report.data.routineReport.industrialAdvancedReverseOsmosis =
                if (isChecked) 1 else 0
            R.id.cbAdvanceMEE -> report.data.routineReport.industrialAdvancedMee =
                if (isChecked) 1 else 0
            R.id.cbAdvanceUltra -> report.data.routineReport.ultraFiltration =
                if (isChecked) 1 else 0
            R.id.cbAdvanceNano -> report.data.routineReport.nanoFiltration = if (isChecked) 1 else 0
            R.id.cbAdvanceATFD -> report.data.routineReport.atfd = if (isChecked) 1 else 0
            R.id.cbAdvanceAnyOther -> report.data.routineReport.industrialAdvancedAnyOther =
                if (isChecked) 1 else 0

            R.id.cbDomesticSepticTank -> report.data.routineReport.treatmentDomesticSepticTank =
                if (isChecked) 1 else 0
            R.id.cbDomesticSewage -> report.data.routineReport.treatmentDomesticSeverageTreatmentPlant =
                if (isChecked) 1 else 0
        }
    }

    private fun onSubmit() {
        report.data.routineReport.treatmentObservation =
            mBinding.edtIndustrialRemark.text.toString()

        saveReportData()
        addReportFragment(Constants.REPORT_4)
    }
}

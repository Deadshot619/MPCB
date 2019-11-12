package com.example.mpcb.reports.treatment


import android.view.View
import android.widget.CompoundButton
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
        mBinding.cbPrimary.setOnCheckedChangeListener { buttonView, isChecked ->
            mBinding.layLinPrimaryChild.visibility = if (isChecked) View.VISIBLE else View.GONE
            report.data.routineReport.treatmentIndustrialPrimary = if (isChecked) 1 else 0
        }

        mBinding.cbSecondary.setOnCheckedChangeListener { buttonView, isChecked ->
            mBinding.layLinSecondaryChild.visibility = if (isChecked) View.VISIBLE else View.GONE
            report.data.routineReport.treatmentIndustrialSecondary = if (isChecked) 1 else 0
        }

        mBinding.cbTertiary.setOnCheckedChangeListener { buttonView, isChecked ->
            mBinding.layLinTertiaryChild.visibility = if (isChecked) View.VISIBLE else View.GONE
            report.data.routineReport.treatmentIndustrialTertiary = if (isChecked) 1 else 0
        }

        mBinding.cbAdvance.setOnCheckedChangeListener { buttonView, isChecked ->
            mBinding.layLinAdvanceChild.visibility = if (isChecked) View.VISIBLE else View.GONE
            report.data.routineReport.treatmentIndustrialAdvanced = if (isChecked) 1 else 0
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

        mBinding.cbSewageActivated.setOnCheckedChangeListener(this)
        mBinding.cbSewageMBBR.setOnCheckedChangeListener(this)
        mBinding.cbSewageSBR.setOnCheckedChangeListener(this)
        mBinding.cbSewageTrickling.setOnCheckedChangeListener(this)
        mBinding.cbSewageAnyOther.setOnCheckedChangeListener(this)
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
                if (isChecked) {
                    mBinding.primaryOtherRemarkLayout.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.primaryOtherRemarkLayout.visibility = View.GONE
                    0
                }

            R.id.cbSecondaryActivated -> report.data.routineReport.industrialSecondaryActivatedSludgeProcess =
                if (isChecked) 1 else 0
            R.id.cbSecondaryMBBR -> report.data.routineReport.industrialSecondaryMbbr =
                if (isChecked) 1 else 0
            R.id.cbSecondarySBR -> report.data.routineReport.industrialSecondarySbr =
                if (isChecked) 1 else 0
            R.id.cbSecondaryTrickling -> report.data.routineReport.industrialSecondaryTricklingFilter =
                if (isChecked) 1 else 0
            R.id.cbSecondaryAnyOther -> report.data.routineReport.industrialSecondaryAnyOther =
                if (isChecked) {
                    mBinding.secondaryOtherRemarkLayout.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.secondaryOtherRemarkLayout.visibility = View.GONE
                    0
                }

            R.id.cbTertiaryPress -> report.data.routineReport.industrialTertiaryPresserSandFilter =
                if (isChecked) 1 else 0
            R.id.cbTertiaryActivated -> report.data.routineReport.industrialTertiaryActivatedCarbonFilter =
                if (isChecked) 1 else 0
            R.id.cbTertiaryDual -> report.data.routineReport.industrialTertiaryDualMediaFilter =
                if (isChecked) 1 else 0
            R.id.cbTertiaryAnyOther -> report.data.routineReport.industrialTertiaryAnyOther =
                if (isChecked) {
                    mBinding.tertiaryOtherRemarkLayout.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.tertiaryOtherRemarkLayout.visibility = View.GONE
                    0
                }

            R.id.cbAdvanceReverse -> report.data.routineReport.industrialAdvancedReverseOsmosis =
                if (isChecked) 1 else 0
            R.id.cbAdvanceMEE -> report.data.routineReport.industrialAdvancedMee =
                if (isChecked) 1 else 0
            R.id.cbAdvanceUltra -> report.data.routineReport.ultraFiltration =
                if (isChecked) 1 else 0
            R.id.cbAdvanceNano -> report.data.routineReport.nanoFiltration = if (isChecked) 1 else 0
            R.id.cbAdvanceATFD -> report.data.routineReport.atfd = if (isChecked) 1 else 0
            R.id.cbAdvanceAnyOther -> report.data.routineReport.industrialAdvancedAnyOther =
                if (isChecked) {
                    mBinding.advanceOtherRemarkLayout.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.advanceOtherRemarkLayout.visibility = View.GONE
                    0
                }

            R.id.cbDomesticSepticTank -> report.data.routineReport.treatmentDomesticSepticTank =
                if (isChecked) 1 else 0
            R.id.cbDomesticSewage -> report.data.routineReport.treatmentDomesticSeverageTreatmentPlant =
                if (isChecked) {
                    mBinding.layLinSewage.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.layLinSewage.visibility = View.GONE
                    0
                }
            R.id.cbSewageActivated -> report.data.routineReport.treatmentDomesticActivatedSludgeProcess =
                if (isChecked) 1 else 0
            R.id.cbSewageMBBR -> report.data.routineReport.treatmentDomesticMbbr =
                if (isChecked) 1 else 0
            R.id.cbSewageSBR -> report.data.routineReport.treatmentDomesticSbr =
                if (isChecked) 1 else 0
            R.id.cbSewageTrickling -> report.data.routineReport.treatmentDomesticTricklingFilter =
                if (isChecked) 1 else 0
            R.id.cbSewageAnyOther -> report.data.routineReport.treatmentDomesticAnyOther =
                if (isChecked) {
                    mBinding.SewageOtherRemarkLayout.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.SewageOtherRemarkLayout.visibility = View.GONE
                    0
                }
        }
    }

    private fun onSubmit() {
        report.data.routineReport.industrialPrimaryAnyOtherText =
            mBinding.edtPrimaryOtherRemark.text.toString()
        report.data.routineReport.industrialSecondaryAnyOtherText =
            mBinding.edtSecondaryOtherRemark.text.toString()
        report.data.routineReport.industrialTertiaryAnyOtherText =
            mBinding.edtTertiaryOtherRemark.text.toString()
        report.data.routineReport.industrialAdvancedAnyOtherText =
            mBinding.edtAdvanceOtherRemark.text.toString()

        if (report.data.routineReport.treatmentIndustrialPrimary == 0) {
            report.data.routineReport.industrialPrimaryOGTrap = 0
            report.data.routineReport.industrialPrimaryScreening = 0
            report.data.routineReport.industrialPrimaryNeutralization = 0
            report.data.routineReport.industrialPrimaryPrimarySettling = 0
            report.data.routineReport.industrialPrimaryAnyOther = 0
            if (report.data.routineReport.industrialPrimaryAnyOther == 0) {
                report.data.routineReport.industrialPrimaryAnyOtherText = ""
            }
        }
        if (report.data.routineReport.treatmentIndustrialSecondary == 0) {
            report.data.routineReport.industrialSecondaryActivatedSludgeProcess = 0
            report.data.routineReport.industrialSecondaryMbbr = 0
            report.data.routineReport.industrialSecondarySbr = 0
            report.data.routineReport.industrialSecondaryTricklingFilter = 0
            report.data.routineReport.industrialSecondaryAnyOther = 0
            if (report.data.routineReport.industrialSecondaryAnyOther == 0) {
                report.data.routineReport.industrialSecondaryAnyOtherText = ""
            }
        }
        if (report.data.routineReport.treatmentIndustrialTertiary == 0) {
            report.data.routineReport.industrialTertiaryPresserSandFilter = 0
            report.data.routineReport.industrialTertiaryActivatedCarbonFilter = 0
            report.data.routineReport.industrialTertiaryDualMediaFilter = 0
            report.data.routineReport.industrialTertiaryAnyOther = 0
            if (report.data.routineReport.industrialTertiaryAnyOther == 0) {
                report.data.routineReport.industrialTertiaryAnyOtherText = ""
            }
        }
        if (report.data.routineReport.treatmentIndustrialAdvanced == 0) {
            report.data.routineReport.industrialAdvancedReverseOsmosis = 0
            report.data.routineReport.industrialAdvancedMee = 0
            report.data.routineReport.ultraFiltration = 0
            report.data.routineReport.nanoFiltration = 0
            report.data.routineReport.atfd = 0
            report.data.routineReport.industrialAdvancedAnyOther = 0
            if (report.data.routineReport.industrialAdvancedAnyOther == 0) {
                report.data.routineReport.industrialAdvancedAnyOtherText = ""
            }
        }
        if (report.data.routineReport.treatmentDomesticSeverageTreatmentPlant == 0) {
            report.data.routineReport.treatmentDomesticActivatedSludgeProcess = 0
            report.data.routineReport.treatmentDomesticMbbr = 0
            report.data.routineReport.treatmentDomesticSbr = 0
            report.data.routineReport.treatmentDomesticTricklingFilter = 0
            report.data.routineReport.treatmentDomesticAnyOther = 0
            if (report.data.routineReport.treatmentDomesticAnyOther == 0) {
                report.data.routineReport.treatmentDomesticAnyOtherText = ""
            }
        }


        report.data.routineReport.treatmentObservation =
            mBinding.edtIndustrialRemark.text.toString()

        if (validate()) {
            saveReportData()
            addReportFragment(Constants.REPORT_4)
        }
    }

    private fun validate(): Boolean {
        if (mBinding.cbPrimary.isChecked) {
            if (!mBinding.cbPrimaryOG.isChecked && !mBinding.cbPrimaryScreening.isChecked && !mBinding.cbPrimaryNeutralization.isChecked
                && !mBinding.cbPrimarySetting.isChecked && !mBinding.cbPrimaryAnyOther.isChecked
            ) {
                showMessage("Select atleast One Primary Option")
                return false
            }
        }
        if (mBinding.cbPrimaryAnyOther.isChecked && mBinding.edtPrimaryOtherRemark.text.isNullOrEmpty()) {
            showMessage("Enter Any Other Text")
            return false
        }

        if (mBinding.cbSecondary.isChecked) {
            if (!mBinding.cbSecondaryActivated.isChecked && !mBinding.cbSecondaryMBBR.isChecked && !mBinding.cbSecondarySBR.isChecked
                && !mBinding.cbSecondaryTrickling.isChecked && !mBinding.cbSecondaryAnyOther.isChecked
            ) {
                showMessage("Select atleast one Secondary Option")
                return false
            }
        }
        if (mBinding.cbSecondaryAnyOther.isChecked && mBinding.edtSecondaryOtherRemark.text.isNullOrEmpty()) {
            showMessage("Enter Any Other Text")
            return false
        }

        if (mBinding.cbTertiary.isChecked) {
            if (!mBinding.cbTertiaryPress.isChecked && !mBinding.cbTertiaryActivated.isChecked
                && !mBinding.cbTertiaryDual.isChecked && !mBinding.cbTertiaryAnyOther.isChecked
            ) {
                showMessage("Select atleast one Tertiary Option")
                return false
            }
        }
        if (mBinding.cbTertiaryAnyOther.isChecked && mBinding.edtTertiaryOtherRemark.text.isNullOrEmpty()) {
            showMessage("Enter Any Other Text")
            return false
        }

        if (mBinding.cbAdvance.isChecked) {
            if (!mBinding.cbAdvanceReverse.isChecked && !mBinding.cbAdvanceMEE.isChecked && !mBinding.cbAdvanceUltra.isChecked
                && !mBinding.cbAdvanceNano.isChecked && !mBinding.cbAdvanceATFD.isChecked && !mBinding.cbAdvanceAnyOther.isChecked
            ) {
                showMessage("Select atleast one Advance Option")
                return false
            }
        }
        if (mBinding.cbAdvanceAnyOther.isChecked && mBinding.edtAdvanceOtherRemark.text.isNullOrEmpty()) {
            showMessage("Enter Any Other Text")
            return false
        }

        if (mBinding.edtIndustrialRemark.text.isNullOrEmpty()) {
            showMessage("Enter Industrial Remark")
            return false
        }

        if (report.data.routineReport.etpOperational == null) {
            showMessage("Select ETP Operational")
            return false
        }

        if (mBinding.cbDomesticSewage.isChecked) {
            if (!mBinding.cbSewageActivated.isChecked && !mBinding.cbSewageMBBR.isChecked && !mBinding.cbSewageSBR.isChecked
                && !mBinding.cbSewageTrickling.isChecked && !mBinding.cbSewageAnyOther.isChecked
            ) {
                showMessage("Select atleast one Sewage Treatment Plant Option")
                return false
            }
        }
        if (mBinding.edtSewageOtherRemark.text.isNullOrEmpty()) {
            showMessage("Enter Any other Remark")
            return false
        }
        if (mBinding.edtDomesticRemark.text.isNullOrEmpty()) {
            showMessage("Enter Any other Remark")
            return false
        }
        if (report.data.routineReport.stpOperational == null) {
            showMessage("Select STP Operational")
            return false
        }

        return true
    }
}

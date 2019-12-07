package com.example.mpcb.reports.treatment


import android.view.View
import android.widget.CompoundButton
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTreatmentBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage


class TreatmentFragment : BaseFragment<FragmentTreatmentBinding, ReportsPageViewModel>(),
    ReportsPageNavigator,
    CompoundButton.OnCheckedChangeListener {


    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_treatment
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreatmentFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_4)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
        showMessage(visitReportId)

        //set report variable data
        setReportVariableData(visitReportId)

        setListeners()
        setCheckBoxListener()
        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_5, visitReportId) }
        }
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

    /**
     * Method to Set listeners to Checkboxes
     */
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
                    mBinding.edtPrimaryOtherRemark.setText("")
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
                    mBinding.edtSecondaryOtherRemark.setText("")
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
                    mBinding.edtTertiaryOtherRemark.setText("")
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
                    mBinding.edtAdvanceOtherRemark.setText("")
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
                    mBinding.edtSewageOtherRemark.setText("")
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

        //Domestic Sewage Any Other text
        report.data.routineReport.treatmentDomesticAnyOtherText =
            mBinding.edtSewageOtherRemark.text.toString()

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
            report.data.routineReport.run{
                treatmentDomesticActivatedSludgeProcess = 0
                treatmentDomesticMbbr = 0
                treatmentDomesticSbr = 0
                treatmentDomesticTricklingFilter = 0
                treatmentDomesticAnyOther = 0
                if (treatmentDomesticAnyOther == 0) {
                    treatmentDomesticAnyOtherText = ""
                }
            }
        }

        //Industrial Remark
        report.data.routineReport.treatmentObservation =
            mBinding.edtIndustrialRemark.text.toString()

        //Domestic Remark
        report.data.routineReport.treatmentDomesticObservation =
            mBinding.edtDomesticRemark.text.toString()



        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_4,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_5, visitReportId)
        }
    }

    /**
     * Method to check if the fields are correctly filled
     */
    private fun validate(): Boolean {
//        INDUSTRIAL

        //Check if either of the industrial types have been selected as
        //one of them has to be selected
        if(!mBinding.cbPrimary.isChecked && !mBinding.cbSecondary.isChecked &&
            !mBinding.cbTertiary.isChecked && !mBinding.cbAdvance.isChecked){
            showMessage("Select atleast one of the Industrial type")
            return false
        }

        /*
         *  Check if Primary checkbox is selected
         *  If yes, then check if any one of the Primary checkboxes is selected.
         */
        if (mBinding.cbPrimary.isChecked) {
            //check if any one of the Primary checkboxes is selected.
            if (!mBinding.cbPrimaryOG.isChecked && !mBinding.cbPrimaryScreening.isChecked && !mBinding.cbPrimaryNeutralization.isChecked
                && !mBinding.cbPrimarySetting.isChecked && !mBinding.cbPrimaryAnyOther.isChecked
            ) {
                showMessage("Select atleast One Primary Option")
                return false
            }

            //Check if Primary any other checkbox is selected.
            //If yes, then check if remark is given or not as it is required
            if (mBinding.cbPrimaryAnyOther.isChecked) {
                if (mBinding.edtPrimaryOtherRemark.text.isNullOrEmpty()) {
                    showMessage("Enter any other remark in Primary type")
                    return false
                }
            }
        }


        /*
         *  Check if Secondary checkbox is selected
         *  If yes, then check if any one of the Secondary checkboxes is selected.
         */
        if (mBinding.cbSecondary.isChecked) {
            //check if any one of the Secondary checkboxes is selected.
            if (!mBinding.cbSecondaryActivated.isChecked && !mBinding.cbSecondaryMBBR.isChecked && !mBinding.cbSecondarySBR.isChecked
                && !mBinding.cbSecondaryTrickling.isChecked && !mBinding.cbSecondaryAnyOther.isChecked
            ) {
                showMessage("Select atleast one Secondary Option")
                return false
            }

            //Check if Secondary any other checkbox is selected.
            //If yes, then check if remark is given or not as it is required
            if (mBinding.cbSecondaryAnyOther.isChecked) {
                if (mBinding.edtSecondaryOtherRemark.text.isNullOrEmpty()) {
                    showMessage("Enter any other remark in Secondary type")
                    return false
                }
            }
        }

        /*
         *  Check if Tertiary checkbox is selected
         *  If yes, then check if any one of the Tertiary checkboxes is selected.
         */
        if (mBinding.cbTertiary.isChecked) {
            //check if any one of the Tertiary checkboxes is selected.
            if (!mBinding.cbTertiaryPress.isChecked && !mBinding.cbTertiaryActivated.isChecked
                && !mBinding.cbTertiaryDual.isChecked && !mBinding.cbTertiaryAnyOther.isChecked
            ) {
                showMessage("Select atleast one Tertiary Option")
                return false
            }

            //Check if Tertiary any other checkbox is selected.
            //If yes, then check if remark is given or not as it is required
            if (mBinding.cbTertiaryAnyOther.isChecked) {
                if (mBinding.edtTertiaryOtherRemark.text.isNullOrEmpty()) {
                    showMessage("Enter any other remark in Secondary type")
                    return false
                }
            }
        }


        /*
         *  Check if Advance checkbox is selected
         *  If yes, then check if any one of the Advance checkboxes is selected.
         */
        if (mBinding.cbAdvance.isChecked) {
            //check if any one of the Advance checkboxes is selected.
            if (!mBinding.cbAdvanceReverse.isChecked && !mBinding.cbAdvanceMEE.isChecked && !mBinding.cbAdvanceUltra.isChecked
                && !mBinding.cbAdvanceNano.isChecked && !mBinding.cbAdvanceATFD.isChecked && !mBinding.cbAdvanceAnyOther.isChecked
            ) {
                showMessage("Select atleast one Advance Option")
                return false
            }

            //Check if Advance any other checkbox is selected.
            //If yes, then check if remark is given or not as it is required
            if (mBinding.cbAdvanceAnyOther.isChecked) {
                if (mBinding.edtAdvanceOtherRemark.text.isNullOrEmpty()) {
                    showMessage("Enter any other remark in Advance type")
                    return false
                }
            }
        }

        //Currently Industrial Remark is not necessary
//        if (mBinding.edtIndustrialRemark.text.isNullOrEmpty()) {
//            showMessage("Enter Industrial Remark")
////            return false
//        }

        //Check if ETP is set or not
        if (report.data.routineReport.etpOperational == null) {
            showMessage("Select ETP Operational")
            return false
        }


//        DOMESTIC
        /*
        *   Check if either of the domestic type is selected or not.
        *   Since atleast one should be checked!
        */
        if (!mBinding.cbDomesticSepticTank.isChecked && !mBinding.cbDomesticSewage.isChecked){
            showMessage("Select any one of the Domestic option")
            return false
        }

        //Check if Domestic Sewage is checked
        if (mBinding.cbDomesticSewage.isChecked) {
            //Check if one of the sewage options is checked
            if (!mBinding.cbSewageActivated.isChecked && !mBinding.cbSewageMBBR.isChecked && !mBinding.cbSewageSBR.isChecked
                && !mBinding.cbSewageTrickling.isChecked && !mBinding.cbSewageAnyOther.isChecked
            ) {
                showMessage("Select atleast one of Sewage Treatment Plant Option")
                return false
            }

            //Check if any other option is selected
            if (mBinding.cbSewageAnyOther.isChecked) {
                if (mBinding.edtSewageOtherRemark.text.isNullOrEmpty()){
                    showMessage("Enter Any other Remark")
                    return false
                }
            }
        }

//        Domestic Remark is not necessary as of now
//        if (mBinding.edtDomesticRemark.text.isNullOrEmpty()) {
//            showMessage("Enter Any other Remark")
//            return false
//        }

        //Check if STP is set or not
        if (report.data.routineReport.stpOperational == null) {
            showMessage("Select STP Operational")
            return false
        }

        return true
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        super.setDataToViews()
        reports  = getReportData(visitReportId)

        if (reports != null){
            mBinding.run {
                //Industrial
                cbPrimary.isChecked = reports?.data?.routineReport?.treatmentIndustrialPrimary == 1
                cbSecondary.isChecked = reports?.data?.routineReport?.treatmentIndustrialSecondary == 1
                cbAdvance.isChecked = reports?.data?.routineReport?.treatmentIndustrialAdvanced == 1
                cbTertiary.isChecked = reports?.data?.routineReport?.treatmentIndustrialTertiary == 1

                //Industrial Primary
                if (cbPrimary.isChecked){
                    cbPrimaryOG.isChecked = reports?.data?.routineReport?.industrialPrimaryOGTrap == 1
                    cbPrimaryScreening.isChecked = reports?.data?.routineReport?.industrialPrimaryScreening == 1
                    cbPrimaryNeutralization.isChecked = reports?.data?.routineReport?.industrialPrimaryNeutralization == 1
                    cbPrimarySetting.isChecked  = reports?.data?.routineReport?.industrialPrimaryPrimarySettling == 1
                    cbPrimaryAnyOther.isChecked = reports?.data?.routineReport?.industrialPrimaryAnyOther == 1
                    if (cbPrimaryAnyOther.isChecked) {
                        edtPrimaryOtherRemark.setText(reports?.data?.routineReport?.industrialPrimaryAnyOtherText)
                    }
                }

                //Industrial Secondary
                if (cbSecondary.isChecked){
                    cbSecondaryActivated.isChecked = reports?.data?.routineReport?.industrialSecondaryActivatedSludgeProcess == 1
                    cbSecondaryMBBR.isChecked = reports?.data?.routineReport?.industrialSecondaryMbbr == 1
                    cbSecondarySBR.isChecked = reports?.data?.routineReport?.industrialSecondarySbr == 1
                    cbSecondaryTrickling.isChecked  = reports?.data?.routineReport?.industrialSecondaryTricklingFilter== 1
                    cbSecondaryAnyOther.isChecked = reports?.data?.routineReport?.industrialSecondaryAnyOther== 1
                    if (cbSecondaryAnyOther.isChecked) {
                        edtSecondaryOtherRemark.setText(reports?.data?.routineReport?.industrialSecondaryAnyOtherText)
                    }
                }

                //Industrial Tertiary
                if (cbTertiary.isChecked){
                    cbTertiaryPress.isChecked = reports?.data?.routineReport?.industrialTertiaryPresserSandFilter == 1
                    cbTertiaryActivated.isChecked = reports?.data?.routineReport?.industrialTertiaryActivatedCarbonFilter == 1
                    cbTertiaryDual.isChecked = reports?.data?.routineReport?.industrialTertiaryDualMediaFilter == 1
                    cbTertiaryAnyOther.isChecked = reports?.data?.routineReport?.industrialTertiaryAnyOther == 1
                    if (cbTertiaryAnyOther.isChecked) {
                        edtTertiaryOtherRemark.setText(reports?.data?.routineReport?.industrialTertiaryAnyOtherText)
                    }
                }

                //Industrial Advance
                if (cbAdvance.isChecked){
                    cbAdvanceReverse.isChecked = reports?.data?.routineReport?.industrialAdvancedReverseOsmosis == 1
                    cbAdvanceMEE.isChecked = reports?.data?.routineReport?.industrialAdvancedMee == 1
                    cbAdvanceUltra.isChecked = reports?.data?.routineReport?.ultraFiltration == 1
                    cbAdvanceNano.isChecked = reports?.data?.routineReport?.nanoFiltration == 1
                    cbAdvanceATFD.isChecked = reports?.data?.routineReport?.atfd == 1
                    cbAdvanceAnyOther.isChecked = reports?.data?.routineReport?.industrialAdvancedAnyOther == 1
                    if (cbAdvanceAnyOther.isChecked) {
                        edtAdvanceOtherRemark.setText(reports?.data?.routineReport?.industrialAdvancedAnyOtherText)
                    }
                }

                //Industrial Remark
                edtIndustrialRemark.setText(reports?.data?.routineReport?.treatmentObservation)

                //Domestic
                //Septic Tank
                cbDomesticSepticTank.isChecked = reports?.data?.routineReport?.treatmentDomesticSepticTank == 1

                //Sewage Treatment
                cbDomesticSewage.isChecked = reports?.data?.routineReport?.treatmentDomesticSeverageTreatmentPlant == 1
                if (cbDomesticSewage.isChecked){
                    cbSewageActivated.isChecked = reports?.data?.routineReport?.treatmentDomesticActivatedSludgeProcess == 1
                    cbSewageMBBR.isChecked = reports?.data?.routineReport?.treatmentDomesticMbbr == 1
                    cbSewageSBR.isChecked = reports?.data?.routineReport?.treatmentDomesticSbr == 1
                    cbSewageTrickling.isChecked = reports?.data?.routineReport?.treatmentDomesticTricklingFilter == 1
                    cbSewageAnyOther.isChecked = reports?.data?.routineReport?.treatmentDomesticAnyOther == 1
                    if (cbSewageAnyOther.isChecked){
                        edtSewageOtherRemark.setText(reports?.data?.routineReport?.treatmentDomesticAnyOtherText)
                    }
                }

                //Domestic Remark
                edtDomesticRemark.setText(reports?.data?.routineReport?.treatmentDomesticObservation)


//              STP Operational
                if (reports?.data?.routineReport?.stpOperational == 1){
                    rgSTP.check(R.id.rbSTPYes)
                }else{
                    rgSTP.check(R.id.rbSTPNo)
                }

//              ETP Operational
                if (reports?.data?.routineReport?.etpOperational == 1){
                    rgETP.check(R.id.rbETPYes)
                }else{
                    rgETP.check(R.id.rbETPNo)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //set data to views in onStart
        setDataToViews()
    }
}

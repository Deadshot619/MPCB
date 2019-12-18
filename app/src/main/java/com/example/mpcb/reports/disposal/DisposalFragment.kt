package com.example.mpcb.reports.disposal

import android.widget.CompoundButton
import androidx.core.widget.addTextChangedListener
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDisposalBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.parseToDouble
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal

class DisposalFragment : BaseFragment<FragmentDisposalBinding, ReportsPageViewModel>(),
    ReportsPageNavigator,
    CompoundButton.OnCheckedChangeListener {


    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    //for Industrial input fields
    private var indusCept: Double = 0.0
    private var indusLandGardening: Double = 0.0
    private var indusRecycle: Double = 0.0
    private var indusLocalSewage: Double = 0.0
    private var indusAnyOther: Double = 0.0

    //for Domestic input fields
    private var domesticCept: Double = 0.0
    private var domesticLandGardening: Double = 0.0
    private var domesticRecycle: Double = 0.0
    private var domesticLocalSewage: Double = 0.0
    private var domesticAnyOther: Double = 0.0

    private val indusTotal: Double
        get() = indusCept + indusLandGardening + indusRecycle + indusLocalSewage + indusAnyOther

    private val domesticTotal: Double
        get() = domesticCept + domesticLandGardening + domesticRecycle + domesticLocalSewage + domesticAnyOther

    override fun getLayoutId() = R.layout.fragment_disposal
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@DisposalFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.disposalParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_5)
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        mBinding.btnSaveNext.run {
            btnSubmit.setOnClickListener { onSubmit() }
            btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_6, visitReportId) }
        }
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

        mBinding.rgIndusDisposalConsent.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.disposalIndustrialAsPerConsent =
                if (checkedId == R.id.rbIndusDisposalNo) "1" else "0"
        }

        mBinding.rgDomesticDisposalConsent.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.disposalDomesticAsPerConsent =
                if (checkedId == R.id.rbDomesticDisposalNo) "1" else "0"
        }

        mBinding.rgIndusOperationMaintenance.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.operationAndMaintainanceInsus = when (checkedId) {
                R.id.rbIndusPoor -> "0"
                R.id.rbIndusAverage -> "1"
                R.id.rbIndusGood -> "2"
                else -> ""
            }
        }

        mBinding.rgDomesticOperationMaintenance.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.operationAndMaintainanceDomestic = when (checkedId) {
                R.id.rbDomesticPoor -> "0"
                R.id.rbDomesticAverage -> "1"
                R.id.rbDomesticGood -> "2"
                else -> ""
            }
        }

        //Industrial Text change Listener
        //This block checks the input fields & sets the text to Total field according to it
        mBinding.run {
            //CEPT
            edIndusCETP.addTextChangedListener {
                indusCept = if (edIndusCETP.text.toString() != "")
                    if (isDecimal(edIndusCETP.text.toString()))
                        edIndusCETP.text.toString().toDouble()
                    else
                        0.0
                else
                    0.0
                edIndustrialTotal.setText(indusTotal.toString())
            }

            //On land gardening
            edIndusLandGardening.addTextChangedListener {
                indusLandGardening = if (edIndusLandGardening.text.toString() != "")
                    if (isDecimal(edIndusLandGardening.text.toString()))
                        edIndusLandGardening.text.toString().toDouble()
                    else
                        0.0
                else
                    0.0
                edIndustrialTotal.setText(indusTotal.toString())
            }

            //Recycle
            edIndusRecycle.addTextChangedListener {
                indusRecycle = if (edIndusRecycle.text.toString() != "")
                    if (isDecimal(edIndusRecycle.text.toString()))
                        edIndusRecycle.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edIndustrialTotal.setText(indusTotal.toString())
            }

            //Local Sewage Treatment
            edIndusSewageTreatment.addTextChangedListener {
                indusLocalSewage = if (edIndusSewageTreatment.text.toString() != "")
                    if (isDecimal(edIndusSewageTreatment.text.toString()))
                        edIndusSewageTreatment.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edIndustrialTotal.setText(indusTotal.toString())
            }

            //Any Other
            edIndusExtraNameValue.addTextChangedListener {
                indusAnyOther = if (edIndusExtraNameValue.text.toString() != "")
                    if (isDecimal(edIndusExtraNameValue.text.toString()))
                        edIndusExtraNameValue.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edIndustrialTotal.setText(indusTotal.toString())
            }

        }

        //Domestic Text change Listener
        //This block checks the input fields & sets the text to Total field according to it
        mBinding.run {
            //CEPT
            edDomesticCETP.addTextChangedListener {
                domesticCept = if (edDomesticCETP.text.toString() != "")
                    if (isDecimal(edDomesticCETP.text.toString()))
                        edDomesticCETP.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edDomesticTotal.setText(domesticTotal.toString())
            }

            //On Land for Gardening
            edDomesticLandGardening.addTextChangedListener {
                domesticLandGardening = if (edDomesticLandGardening.text.toString() != "")
                    if (isDecimal(edDomesticLandGardening.text.toString()))
                        edDomesticLandGardening.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edDomesticTotal.setText(domesticTotal.toString())
            }

            //Recycle
            edDomesticRecycle.addTextChangedListener {
                domesticRecycle = if (edDomesticRecycle.text.toString() != "")
                    if (isDecimal(edDomesticRecycle.text.toString()))
                        edDomesticRecycle.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edDomesticTotal.setText(domesticTotal.toString())
            }

            //Local Sewage Treatment
            edDomesticSewageTreatment.addTextChangedListener {
                domesticLocalSewage = if (edDomesticSewageTreatment.text.toString() != "")
                    if (isDecimal(edDomesticSewageTreatment.text.toString()))
                        edDomesticSewageTreatment.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edDomesticTotal.setText(domesticTotal.toString())
            }

            //Any Other
            edDomesticExtraNameValue.addTextChangedListener {
                domesticAnyOther = if (edDomesticExtraNameValue.text.toString() != "")
                    if (isDecimal(edDomesticExtraNameValue.text.toString()))
                        edDomesticExtraNameValue.text.toString().toDouble()
                    else 0.0
                else
                    0.0
                edDomesticTotal.setText(domesticTotal.toString())
            }

        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        //Set listeners to checkbox when Visit Status is is Not Visited
        if (!visitStatus)
            mBinding.run {
                when (buttonView!!.id) {
                    /**
                     * In this method fields are enabled & disabled according to the checkbox state.
                     * If the checkbox is checked, Field is enabled.
                     * If the checkbox is not checked, Field is disabled & cleared.
                     */

//                Industrial
                    R.id.cbIndusCETP -> report.data.routineReport.disposalIndustrialCETP =
                        if (isChecked) {
                            edIndusCETP.isEnabled = true
                            1
                        } else {
                            edIndusCETP.isEnabled = false
                            edIndusCETP.setText("")
                            0
                        }

                    R.id.cbIndusLandGardening -> report.data.routineReport.disposalIndustrialLandGardening =
                        if (isChecked) {
                            edIndusLandGardening.isEnabled = true
                            1
                        } else {
                            edIndusLandGardening.isEnabled = false
                            edIndusLandGardening.setText("")
                            0
                        }

                    R.id.cbIndusRecycle -> report.data.routineReport.disposalIndustrialRecycle =
                        if (isChecked) {
                            edIndusRecycle.isEnabled = true
                            1
                        } else {
                            edIndusRecycle.isEnabled = false
                            edIndusRecycle.setText("")
                            0
                        }

                    R.id.cbIndusSewageTreatment -> report.data.routineReport.disposalIndustrialLocalBodySewage =
                        if (isChecked) {
                            edIndusSewageTreatment.isEnabled = true
                            1
                        } else {
                            edIndusSewageTreatment.isEnabled = false
                            edIndusSewageTreatment.setText("")
                            0
                        }

                    R.id.cbIndusAnyOther -> report.data.routineReport.disposalIndustrialAnyOther =
                        if (isChecked) {
                            edIndusExtraName.isEnabled = true
                            edIndusExtraNameValue.isEnabled = true
                            1
                        } else {
                            edIndusExtraName.isEnabled = false
                            edIndusExtraNameValue.isEnabled = false
                            edIndusExtraName.setText("")
                            edIndusExtraNameValue.setText("")
                            0
                        }

                    //Domestic
                    R.id.cbDomesticCETP -> report.data.routineReport.disposalDomesticCETP =
                        if (isChecked) {
                            edDomesticCETP.isEnabled = true
                            1
                        } else {
                            edDomesticCETP.isEnabled = false
                            edDomesticCETP.setText("")
                            0
                        }

                    R.id.cbDomesticLandGardening -> report.data.routineReport.disposalDomesticLandGardening =
                        if (isChecked) {
                            edDomesticLandGardening.isEnabled = true
                            1
                        } else {
                            edDomesticLandGardening.isEnabled = false
                            edDomesticLandGardening.setText("")
                            0
                        }

                    R.id.cbDomesticRecycle -> report.data.routineReport.disposalDomesticRecycle =
                        if (isChecked) {
                            edDomesticRecycle.isEnabled = true
                            1
                        } else {
                            edDomesticRecycle.isEnabled = false
                            edDomesticRecycle.setText("")
                            0
                        }

                    R.id.cbDomesticSewageTreatment -> report.data.routineReport.disposalDomesticLocalBodySewage =
                        if (isChecked) {
                            edDomesticSewageTreatment.isEnabled = true
                            1
                        } else {
                            edDomesticSewageTreatment.isEnabled = false
                            edDomesticSewageTreatment.setText("")
                            0
                        }

                    R.id.cbDomesticAnyOther -> report.data.routineReport.disposalDomesticAnyOther =
                        if (isChecked) {
                            edDomesticExtraName.isEnabled = true
                            edDomesticExtraNameValue.isEnabled = true
                            1
                        } else {
                            edDomesticExtraName.isEnabled = false
                            edDomesticExtraNameValue.isEnabled = false
                            edDomesticExtraName.setText("")
                            edDomesticExtraNameValue.setText("")
                            0
                        }
                }
            }

    }

    private fun onSubmit() {
        report.data.routineReport.disposalIndustrialCETPText =
            mBinding.edIndusCETP.text.toString()
        report.data.routineReport.disposalIndustrialLandGardeningText =
            mBinding.edIndusLandGardening.text.toString()
        report.data.routineReport.disposalIndustrialRecycleText =
            mBinding.edIndusRecycle.text.toString()
        report.data.routineReport.disposalIndustrialLocalBodySewageText =
            mBinding.edIndusSewageTreatment.text.toString()
        report.data.routineReport.disposalIndustrialAnyOtherText =
            mBinding.edIndusExtraName.text.toString()
        report.data.routineReport.disposalIndustrialAnyOtherTextRemarks =
            mBinding.edIndusExtraNameValue.text.toString()
        report.data.routineReport.disposalIndustrialTotal =
            mBinding.edIndustrialTotal.text.toString().parseToDouble()


        report.data.routineReport.disposalDomesticCETPText =
            mBinding.edDomesticCETP.text.toString()
        report.data.routineReport.disposalDomesticLandGardeningText =
            mBinding.edDomesticLandGardening.text.toString()
        report.data.routineReport.disposalDomesticRecycleText =
            mBinding.edDomesticRecycle.text.toString()
        report.data.routineReport.disposalDomesticLocalBodySewageText =
            mBinding.edDomesticSewageTreatment.text.toString()
        report.data.routineReport.disposalDomesticAnyOtherTextRemarks =
            mBinding.edDomesticExtraNameValue.text.toString()
        report.data.routineReport.disposalDomesticAnyOtherText =
            mBinding.edDomesticExtraName.text.toString()
        report.data.routineReport.disposalDomesticTotal =
            mBinding.edDomesticTotal.text.toString().parseToDouble()

        report.data.routineReport.disposalObservation = mBinding.edRemark.text.toString()

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_5,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_6, visitReportId)
        }
    }

    private fun validate(): Boolean {
        mBinding.run {
            //        Industrial

            //Check if either of the industrial types have been selected as
            //one of them has to be selected
            if (!cbIndusCETP.isChecked && !cbIndusLandGardening.isChecked &&
                !cbIndusRecycle.isChecked && !cbIndusSewageTreatment.isChecked
                && !cbIndusAnyOther.isChecked
            ) {
                showMessage("Select atleast one of the Industrial type")
                return false
            }

            if (cbIndusCETP.isChecked) {
                if (edIndusCETP.text.isNullOrEmpty()) {
                    showMessage("Enter CEPT for Industrial")
                    return false
                } else if (!isDecimal(edIndusCETP.text.toString())) {
                    showMessage("Invalid CEPT for Industrial value")
                    return false
                }
            }
            if (cbIndusLandGardening.isChecked) {
                if (edIndusLandGardening.text.isNullOrEmpty()) {
                    showMessage("Enter Land gardening for Industrial")
                    return false
                } else if (!isDecimal(edIndusLandGardening.text.toString())) {
                    showMessage("Invalid Land gardening for Industrial value")
                    return false
                }
            }
            if (cbIndusRecycle.isChecked) {
                if (edIndusRecycle.text.isNullOrEmpty()) {
                    showMessage("Enter Recycle for Industrial")
                    return false
                } else if (!isDecimal(edIndusRecycle.text.toString())) {
                    showMessage("Invalid Recycle for Industrial value")
                    return false
                }
            }
            if (cbIndusSewageTreatment.isChecked) {
                if (edIndusSewageTreatment.text.isNullOrEmpty()) {
                    showMessage("Enter Local Sewage Treatment for Industrial")
                    return false
                } else if (!isDecimal(edIndusSewageTreatment.text.toString())) {
                    showMessage("Invalid Local Sewage Treatment for Industrial value")
                    return false
                }
            }
            if (cbIndusAnyOther.isChecked) {
                if (edIndusExtraName.text.isNullOrEmpty()) {
                    showMessage("Enter Any Other Text for Industrial")
                    return false
                }
                if (edIndusExtraNameValue.text.isNullOrEmpty()) {
                    showMessage("Enter Any Other Value for Industrial")
                    return false
                } else if (!isDecimal(edIndusExtraNameValue.text.toString())) {
                    showMessage("Invalid Any Other Value for Industrial value")
                    return false
                }
            }

//        Disposable checkbox
            if (report.data.routineReport.disposalIndustrialAsPerConsent.isEmpty()) {
                showMessage("Select Disposal As Per Consent for Industrial")
                return false
            }

//        Operation & Maintenance checkbox
            if (report.data.routineReport.operationAndMaintainanceInsus.isEmpty()) {
                showMessage("Select Operation And Maintenance for Industrial")
                return false
            }

//        Domestic

            //Check if either of the industrial types have been selected as
            //one of them has to be selected
            if (!cbDomesticCETP.isChecked && !cbDomesticLandGardening.isChecked &&
                !cbDomesticRecycle.isChecked && !cbDomesticSewageTreatment.isChecked
                && !cbDomesticAnyOther.isChecked
            ) {
                showMessage("Select atleast one of the Domestic type")
                return false
            }

            if (cbDomesticCETP.isChecked) {
                if (edDomesticCETP.text.isNullOrEmpty()) {
                    showMessage("Enter CEPT for Domestic")
                    return false
                } else if (!isDecimal(edDomesticCETP.text.toString())) {
                    showMessage("Invalid CEPT for Domestic value")
                    return false
                }
            }
            if (cbDomesticLandGardening.isChecked) {
                if (edDomesticLandGardening.text.isNullOrEmpty()) {
                    showMessage("Enter Land gardening for Domestic")
                    return false
                } else if (!isDecimal(edDomesticLandGardening.text.toString())) {
                    showMessage("Invalid Land gardening for Domestic value")
                    return false
                }
            }
            if (cbDomesticRecycle.isChecked) {
                if (edDomesticRecycle.text.isNullOrEmpty()) {
                    showMessage("Enter Recycle for Domestic")
                    return false
                } else if (!isDecimal(edDomesticRecycle.text.toString())) {
                    showMessage("Invalid Recycle for Domestic value")
                    return false
                }
            }
            if (cbDomesticSewageTreatment.isChecked) {
                if (edDomesticSewageTreatment.text.isNullOrEmpty()) {
                    showMessage("Enter Local Sewage Treatment for Domestic")
                    return false
                } else if (!isDecimal(edDomesticSewageTreatment.text.toString())) {
                    showMessage("Invalid Local Sewage Treatment for Domestic value")
                    return false
                }
            }
            if (cbDomesticAnyOther.isChecked) {
                if (edDomesticExtraName.text.isNullOrEmpty()) {
                    showMessage("Enter Any Other Text for Domestic")
                    return false
                }
                if (edDomesticExtraNameValue.text.isNullOrEmpty()) {
                    showMessage("Enter Any Other Value for Domestic")
                    return false
                } else if (!isDecimal(edDomesticExtraNameValue.text.toString())) {
                    showMessage("Invalid Any Other Value for Domestic value")
                    return false
                }
            }

//        Domestic Disposable checkbox
            if (report.data.routineReport.disposalDomesticAsPerConsent.isEmpty()) {
                showMessage("Select Disposal As Per Consent for Domestic")
                return false
            }

            //        Domestic Operation & Maintenance checkbox
            if (report.data.routineReport.operationAndMaintainanceDomestic.isEmpty()) {
                showMessage("Select Operation And Maintenance for Domestic")
                return false
            }
        }
        return true
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        reports = if (visitStatus) {
            getReportData(Constants.TEMP_VISIT_REPORT_DATA)
        } else {
            getReportData(visitReportId)
        }

        if (reports != null) {
            mBinding.run {
                reports?.data?.routineReport?.run {
//                    Industrial checkboxes
                    cbIndusCETP.isChecked = disposalIndustrialCETP == 1
                    cbIndusLandGardening.isChecked = disposalIndustrialLandGardening == 1
                    cbIndusRecycle.isChecked = disposalIndustrialRecycle == 1
                    cbIndusSewageTreatment.isChecked = disposalIndustrialLocalBodySewage == 1
                    cbIndusAnyOther.isChecked = disposalIndustrialAnyOther == 1

//                    Industrial checkboxes text
                    if (cbIndusCETP.isChecked)
                        edIndusCETP.setText(disposalIndustrialCETPText)
                    if (cbIndusLandGardening.isChecked)
                        edIndusLandGardening.setText(disposalIndustrialLandGardeningText)
                    if (cbIndusRecycle.isChecked)
                        edIndusRecycle.setText(disposalIndustrialRecycleText)
                    if (cbIndusSewageTreatment.isChecked)
                        edIndusSewageTreatment.setText(disposalIndustrialLocalBodySewageText)
                    if (cbIndusAnyOther.isChecked) {
                        edIndusExtraName.setText(disposalIndustrialAnyOtherText)
                        edIndusExtraNameValue.setText(disposalIndustrialAnyOtherTextRemarks)
                    }

//                    Industrial Disposal Consent
                    if (disposalIndustrialAsPerConsent == "1") {
                        rgIndusDisposalConsent.check(R.id.rbIndusDisposalNo)
                    } else {
                        rgIndusDisposalConsent.check(R.id.rbIndusDisposalYes)
                    }

//                    Industrial Operation & Maintenance
                    when (operationAndMaintainanceInsus) {
                        "0" -> rgIndusOperationMaintenance.check(R.id.rbIndusPoor)
                        "1" -> rgIndusOperationMaintenance.check(R.id.rbIndusAverage)
                        "2" -> rgIndusOperationMaintenance.check(R.id.rbIndusGood)
                    }

//                    DOMESTIC
                    cbDomesticCETP.isChecked = disposalDomesticCETP == 1
                    cbDomesticLandGardening.isChecked = disposalDomesticLandGardening == 1
                    cbDomesticRecycle.isChecked = disposalDomesticRecycle == 1
                    cbDomesticSewageTreatment.isChecked = disposalDomesticLocalBodySewage == 1
                    cbDomesticAnyOther.isChecked = disposalDomesticAnyOther == 1

//                    Domestic checkboxes text
                    if (cbDomesticCETP.isChecked)
                        edDomesticCETP.setText(disposalDomesticCETPText)
                    if (cbDomesticLandGardening.isChecked)
                        edDomesticLandGardening.setText(disposalDomesticLandGardeningText)
                    if (cbDomesticRecycle.isChecked)
                        edDomesticRecycle.setText(disposalDomesticRecycleText)
                    if (cbDomesticSewageTreatment.isChecked)
                        edDomesticSewageTreatment.setText(disposalDomesticLocalBodySewageText)
                    if (cbDomesticAnyOther.isChecked) {
                        edDomesticExtraName.setText(disposalDomesticAnyOtherText)
                        edDomesticExtraNameValue.setText(disposalDomesticAnyOtherTextRemarks)
                    }

//                    Domestic Disposal Consent
                    if (disposalDomesticAsPerConsent == "1") {
                        rgDomesticDisposalConsent.check(R.id.rbDomesticDisposalNo)
                    } else {
                        rgDomesticDisposalConsent.check(R.id.rbDomesticDisposalYes)
                    }

//                    Domestic Operation & Maintenance
                    when (operationAndMaintainanceDomestic) {
                        "0" -> rgDomesticOperationMaintenance.check(R.id.rbDomesticPoor)
                        "1" -> rgDomesticOperationMaintenance.check(R.id.rbDomesticAverage)
                        "2" -> rgDomesticOperationMaintenance.check(R.id.rbDomesticGood)
                    }

//                    Disposal remark
                    edRemark.setText(disposalObservation)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
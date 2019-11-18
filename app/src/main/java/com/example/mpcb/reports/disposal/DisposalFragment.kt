package com.example.mpcb.reports.disposal

import android.widget.CompoundButton
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDisposalBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.parseToInt
import com.example.mpcb.utils.showMessage

class DisposalFragment : BaseFragment<FragmentDisposalBinding, ReportsPageViewModel>(),
    ReportsPageNavigator,
    CompoundButton.OnCheckedChangeListener {


    private var reports: ReportRequest? = null

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

        mBinding.rgIndusDisposalConsent.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.disposalIndustrialAsPerConsent =
                if (checkedId == R.id.rbIndusDisposalYes) "1" else "0"
        }

        mBinding.rgDomesticDisposalConsent.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.disposalDomesticAsPerConsent =
                if (checkedId == R.id.rbDomesticDisposalYes) "1" else "0"
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
            mBinding.edIndustrialTotal.text.toString().parseToInt()


        report.data.routineReport.disposalDomesticCETPText =
            mBinding.edDomesticCETP.text.toString()
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

        if (validate()) {
            saveReportData(
                reportKey = Constants.REPORT_5,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_6)
        }
    }

    private fun validate(): Boolean {
//        Industrial

        //Check if either of the industrial types have been selected as
        //one of them has to be selected
        if (!mBinding.cbIndusCETP.isChecked && !mBinding.cbIndusLandGardening.isChecked &&
                !mBinding.cbIndusRecycle.isChecked && !mBinding.cbIndusSewageTreatment.isChecked
            && !mBinding.cbIndusAnyOther.isChecked){
            showMessage("Select atleast one of the Industrial type")
            return false
        }

        if (mBinding.cbIndusCETP.isChecked) {
            if (mBinding.edIndusCETP.text.isNullOrEmpty()) {
                showMessage("Enter CEPT for Industrial")
                return false
            }
        }
        if (mBinding.cbIndusLandGardening.isChecked) {
            if (mBinding.edIndusLandGardening.text.isNullOrEmpty()) {
                showMessage("Enter Land gardening for Industrial")
                return false
            }
        }
        if (mBinding.cbIndusRecycle.isChecked) {
            if (mBinding.edIndusRecycle.text.isNullOrEmpty()) {
                showMessage("Enter Recycle for Industrial")
                return false
            }
        }
        if (mBinding.cbIndusSewageTreatment.isChecked) {
            if (mBinding.edIndusSewageTreatment.text.isNullOrEmpty()) {
                showMessage("Enter Local Sewage Treatment for Industrial")
                return false
            }
        }
        if (mBinding.cbIndusAnyOther.isChecked) {
            if (mBinding.edIndusExtraName.text.isNullOrEmpty()) {
                showMessage("Enter Any Other Text for Industrial")
                return false
            }
            if (mBinding.edIndusExtraNameValue.text.isNullOrEmpty()) {
                showMessage("Enter Any Other Value for Industrial")
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
        if (!mBinding.cbDomesticCETP.isChecked && !mBinding.cbDomesticLandGardening.isChecked &&
                !mBinding.cbDomesticRecycle.isChecked && !mBinding.cbDomesticSewageTreatment.isChecked
            && !mBinding.cbDomesticAnyOther.isChecked){
            showMessage("Select atleast one of the Domestic type")
            return false
        }

        if (mBinding.cbDomesticCETP.isChecked) {
            if (mBinding.edDomesticCETP.text.isNullOrEmpty()) {
                showMessage("Enter CEPT for Domestic")
                return false
            }
        }
        if (mBinding.cbDomesticLandGardening.isChecked) {
            if (mBinding.edDomesticLandGardening.text.isNullOrEmpty()) {
                showMessage("Enter Land gardening for Domestic")
                return false
            }
        }
        if (mBinding.cbDomesticRecycle.isChecked) {
            if (mBinding.edDomesticRecycle.text.isNullOrEmpty()) {
                showMessage("Enter Recycle for Domestic")
                return false
            }
        }
        if (mBinding.cbDomesticSewageTreatment.isChecked) {
            if (mBinding.edDomesticSewageTreatment.text.isNullOrEmpty()) {
                showMessage("Enter Local Sewage Treatment for Domestic")
                return false
            }
        }
        if (mBinding.cbDomesticAnyOther.isChecked) {
            if (mBinding.edDomesticExtraName.text.isNullOrEmpty()) {
                showMessage("Enter Any Other Text for Domestic")
                return false
            }
            if (mBinding.edDomesticExtraNameValue.text.isNullOrEmpty()) {
                showMessage("Enter Any Other Value for Domestic")
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

        return true
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        super.setDataToViews()
        reports = getReportData()

        if (reports != null){
            mBinding.run {
                reports?.data?.routineReport?.run{
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
                    if (disposalIndustrialAsPerConsent == "1"){
                        rgIndusDisposalConsent.check(R.id.rbIndusDisposalYes)
                    }else{
                        rgIndusDisposalConsent.check(R.id.rbIndusDisposalNo)
                    }

//                    Indutrial Operatioon & Maintenance
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
                    if (disposalDomesticAsPerConsent == "1"){
                        rgDomesticDisposalConsent.check(R.id.rbDomesticDisposalYes)
                    }else{
                        rgDomesticDisposalConsent.check(R.id.rbDomesticDisposalNo)
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

    override fun onResume() {
        super.onResume()
    }
}
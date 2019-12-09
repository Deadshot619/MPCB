package com.example.mpcb.reports.oms_ambient_air


import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentOmsAmbientAirBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import com.example.mpcb.utils.validations.isDecimal

class OMSAmbientAirFragment : BaseFragment<FragmentOmsAmbientAirBinding, OMSAmbientAirViewModel>(),
    OMSAmbientAirNavigator {
    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_oms_ambient_air
    override fun getViewModel() = OMSAmbientAirViewModel::class.java
    override fun getNavigator() = this@OMSAmbientAirFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_11)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()
        setListeners()

        mBinding.run {
            tvAddMore.setOnClickListener { mViewModel.addItem() }
            imgDelete.setOnClickListener { mViewModel.deleteItem() }
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
                btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_12, visitReportId) }
            }
        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvAmbientAir.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = AmbientAirAdapter(getBaseActivity(), mViewModel, visitStatus)
        mBinding.rvAmbientAir.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun setListeners() {
        mBinding.run {
            report.data.routineReport.run {

                //OMS
                rgOMS.setOnCheckedChangeListener { group, checkedId ->
                    omsamApplicable =
                        if (checkedId == R.id.rbOMSApplicable) {
                            showHideView(true)
                            1
                        } else {
                            showHideView(false)
                            0
                        }
                }

                //OMS Installed
                rgOMSInstalled.setOnCheckedChangeListener { group, checkedId ->
                    omsamInstalled =
                        if (checkedId == R.id.rbOMSInstalledApplicable) {
                            txtConnectivity.visibility = View.VISIBLE
                            linLayConnectivity.visibility = View.VISIBLE
                            1
                        } else {
                            txtConnectivity.visibility = View.GONE
                            linLayConnectivity.visibility = View.GONE

                            cbCPCB.isChecked = false
                            cbMPCB.isChecked = false

                            0
                        }
                }

//                JVS Sample Collected
                rgSampleCollected.setOnCheckedChangeListener { group, checkedId ->
                    jvsSampleCollectedForAir =
                        if (checkedId == R.id.rbSampleYes) {
                            rvAmbientAir.visibility = View.VISIBLE

                            if (!visitStatus) {
                                tvAddMore.visibility = View.VISIBLE
                                imgDelete.visibility = View.VISIBLE
                            }
                            1
                        } else {
                            rvAmbientAir.visibility = View.GONE

                            if (!visitStatus) {
                                tvAddMore.visibility = View.GONE
                                imgDelete.visibility = View.GONE
                            }
                            0
                        }
                }

//                CPCB
                cbCPCB.setOnCheckedChangeListener { buttonView, isChecked ->
                    omsamCpcb = if (isChecked) 1 else 0
                }

//                MPCB
                cbMPCB.setOnCheckedChangeListener { buttonView, isChecked ->
                    omsamMpcb = if (isChecked) 1 else 0
                }
            }
        }
    }

    private fun showHideView(showView: Boolean) {
        if (showView) {
            mBinding.txtOMSInstalled.visibility = View.VISIBLE
            mBinding.rgOMSInstalled.visibility = View.VISIBLE
//            mBinding.txtConnectivity.visibility = View.VISIBLE
//            mBinding.linLayConnectivity.visibility = View.VISIBLE

            //set check to Not Installed
            mBinding.rgOMSInstalled.check(R.id.rbOMSInstalledNotApplicable)
//            mBinding.cbCPCB.visibility = View.VISIBLE
//            mBinding.cbMPCB.visibility = View.VISIBLE
        } else {
            mBinding.txtOMSInstalled.visibility = View.GONE
            mBinding.rgOMSInstalled.visibility = View.GONE
//            mBinding.txtConnectivity.visibility = View.GONE
//            mBinding.linLayConnectivity.visibility = View.GONE

            //set check to Not Installed
            mBinding.rgOMSInstalled.check(R.id.rbOMSInstalledNotApplicable)

//            mBinding.cbCPCB.visibility = View.GONE
//            mBinding.cbMPCB.visibility = View.GONE
        }
    }

    private fun onSubmit() {
        //OMS
        if (report.data.routineReport.omsamApplicable == 0) {
            report.data.routineReport.omsamInstalled = 0
            report.data.routineReport.omsamCpcb = 0
            report.data.routineReport.omsamMpcb = 0
        }

        if(report.data.routineReport.jvsSampleCollectedForAir == 0){
            report.data.jvsSampleCollectedAirSource = arrayListOf()
        }

        //Remark
        report.data.routineReport.jvsObservation = mBinding.edtRemark.text.toString()


        if (report.data.routineReport.jvsSampleCollectedForAir == 1) {
            report.data.jvsSampleCollectedAirSource = mViewModel.getReportData()
        }


        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_11,
                reportStatus = true
            )

            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_12, visitReportId)
        }
    }


    private fun validate(): Boolean {

        mBinding.run {
            //OMS
            if (!rbOMSApplicable.isChecked && !rbOMSNotApplicable.isChecked) {
                showMessage("Select Online Monitoring System")
                return false
            }

            //if OMS checked
            if (rbOMSApplicable.isChecked) {

                if (!rbOMSInstalledApplicable.isChecked && !rbOMSInstalledNotApplicable.isChecked) {
                    showMessage("Select Online Monitoring System Installed")
                    return false
                }

                //Connectivity
                if (rbOMSInstalledApplicable.isChecked ){
                    if (!cbCPCB.isChecked && !cbMPCB.isChecked) {
                        showMessage("Select Connectivity")
                        return false
                    }
                }
            }

            //JVS
            if (!rbSampleYes.isChecked && !rbSampleNo.isChecked) {
                showMessage("Select JVS Sample")
                return false
            }

            //Remark
//            if (edtRemark.text.isNullOrEmpty()) {
//                showMessage("Enter Remarks")
//                return false
//            }
        }

        var isValid = true
        val sampleList = mViewModel.getReportData()

        if (mBinding.rbSampleYes.isChecked) {
            outer@ for (item in sampleList) {
                if (item.nameOfSource.isEmpty()) {
                    showMessage("Enter Source")
                    isValid = false
                    break
                }
                for (childItem in item.ambientAirChild) {
                    if (childItem.prescribedValue.isEmpty()) {
                        showMessage("Enter Prescribed Value")
                        isValid = false
                        break@outer
                    }else if (!isDecimal(childItem.prescribedValue)){
                        showMessage("Invalid Prescribed value.")
                        isValid = false
                        break@outer
                    }
                }
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


        if (reports != null){

            mBinding.run{
                reports?.data?.routineReport?.run {
                    //OMS
                    if (omsamApplicable == 1){
                        rgOMS.check(R.id.rbOMSApplicable)
                    }else{
                        rgOMS.check(R.id.rbOMSNotApplicable)
                    }

                    //Check if OMS is selected
                    if (omsamApplicable == 1){
                        //OMS Installed
                        if (omsamInstalled == 1)
                            rgOMSInstalled.check(R.id.rbOMSInstalledApplicable)
                        else
                            rgOMSInstalled.check(R.id.rbOMSInstalledNotApplicable)

                        //Check if OMS installed is selected
                        if (omsamInstalled == 1){
                            cbCPCB.isChecked = omsamCpcb == 1
                            cbMPCB.isChecked = omsamMpcb == 1
                        }


                    }

                    //JVS Sample Collected
                    if (jvsSampleCollectedForAir == 1)
                        rgSampleCollected.check(R.id.rbSampleYes)
                    else
                        rgSampleCollected.check(R.id.rbSampleNo)

                    //Remark
                    edtRemark.setText(jvsObservation)

//                        if (jvsSampleCollectedForAir == 1)
                    if (reports?.data?.jvsSampleCollectedAirSource != null)
                        mViewModel.populateData(reports?.data?.jvsSampleCollectedAirSource)

                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}

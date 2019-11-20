package com.example.mpcb.reports.air_pollution

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAirPollutionBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class AirFragment : BaseFragment<FragmentAirPollutionBinding, AirViewModel>(), AirNavigator {

    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_air_pollution
    override fun getViewModel() = AirViewModel::class.java
    override fun getNavigator() = this@AirFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_9)
        setUpRecyclerView()

        mBinding.btnSubmit.setOnClickListener { onSubmit() }
        mBinding.imgDelete.setOnClickListener { mViewModel.deleteItem() }
        mBinding.imgAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
    }

    private fun setUpRecyclerView() {
        mBinding.rvSource.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = AirPollutionAdapter(getBaseActivity(), mViewModel)
        mBinding.rvSource.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun onSubmit() {
//        addReportFragment(Constants.REPORT_10)
        if (validate()) {
            report.data.routineReportAirPollution.clear()
            report.data.routineReport.airPollutionObservation = mBinding.edtRemarks.text.toString()
            report.data.routineReportAirPollution = mViewModel.getSourceList().value!!

            saveReportData(
                reportKey = Constants.REPORT_9,
                reportStatus = true
            )

            addReportFragment(Constants.REPORT_10)
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        val sourceList = mViewModel.getSourceList().value!!
        for (item in sourceList) {
            //Source other
            if (item.airPollutionSource == "5"){
                if (item.airPollutionSourceOther.isNullOrEmpty()){
                    showMessage("Please enter any other source")
                    isValid = false
                    break
                }
            }

            if (item.airPollutionType.isNullOrEmpty()) {
                showMessage("Select Process / Fuel Burning")
                isValid = false
                break
            }

            //Pollution type Fuel Burning selected
            if (item.airPollutionType == "0"){
                //Fuel Name
                if (item.airPollutionFuelName.isEmpty()) {
                    showMessage("Enter Fuel Name")
                    isValid = false
                    break
                }

                //Fuel Quantity
                if (item.airPollutionFuelQuantity.isEmpty()) {
                    showMessage("Enter Fuel Quantity")
                    isValid = false
                    break
                }

                //Fuel Unit
                if (item.airPollutionFuelUnit.isEmpty()) {
                    showMessage("Enter Fuel Unit")
                    isValid = false
                    break
                }
            }

//            Pollutants
            if (item.airPollutionPollutants.isEmpty()) {
                showMessage("Enter Pollutants")
                isValid = false
                break
            }

//            Checkboxes
            if (!(item.airPollutionMechDustCollector == 1 || item.airPollutionCycloneDustCollector == 1
                || item.airPollutionMultiDustCollector == 1 || item.airPollutionFabricBagFilter == 1
                || item.airPollutionPackageTower == 1 || item.airPollutionVenturiScrubber == 1
                || item.airPollutionElectroStatic == 1 || item.airPollutionNoProvision == 1
                || item.airPollutionAnyOther == 1)){
                showMessage("Please check atleast one of the checkbox")
                isValid = false
                break
            }


            if (item.airPollutionStackHeight.isEmpty()) {
                showMessage("Enter Stack Height")
                isValid = false
                break
            }
        }

        if (isValid && report.data.routineReport.airPollutionObservation.isNullOrEmpty()) {
            showMessage("Enter Remarks")
            return false
        }

        return isValid
    }

    /**
     * This method is used to retrieve & set data to views
     */
    override fun setDataToViews() {
        reports= getReportData()

//        Air Pollution Remarl/Observation
        mBinding.edtRemarks.setText(reports?.data?.routineReport?.airPollutionObservation)

//        Air Pollution data
        if (reports?.data?.routineReportAirPollution != null)
            mViewModel.populateData(reports?.data?.routineReportAirPollution)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }
}
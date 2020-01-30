package com.gov.mpcb.reports.last_jvs_details


import android.app.DatePickerDialog
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentLastJvsBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.utils.validations.isDecimal
import java.util.*

class LastJVSFragment : BaseFragment<FragmentLastJvsBinding, LastJVSViewModel>(), LastJVSNavigator {

    private val INDUS_DATE_OF_COLLECTION = 1
    private val INDUS_DATE = 2
    private val DOMESTIC_DATE_OF_COLLECTION = 3
    private val DOMESTIC_DATE = 4

    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_last_jvs
    override fun getViewModel() = LastJVSViewModel::class.java
    override fun getNavigator() = this@LastJVSFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextAndPreviousButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_8)
        setUpRecyclerView()
        setListener()

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        mBinding.run {

            edtIndusDateOfCollection.setOnClickListener {
                showDateDialog(INDUS_DATE_OF_COLLECTION)
            }
            edtDateIndus.setOnClickListener { showDateDialog(INDUS_DATE) }
            edtDomesticDateOfCollection.setOnClickListener {
                showDateDialog(DOMESTIC_DATE_OF_COLLECTION)
            }
            edtDateDomestic.setOnClickListener { showDateDialog(DOMESTIC_DATE) }

            tvAddMore.setOnClickListener { mViewModel.addItem() }
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
                btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_9, visitReportId) }
            }

            //Delete Button
            imgDelete.setOnClickListener {
                mViewModel.deleteItem()
            }
        }

    }

    private fun setUpRecyclerView() {
        mBinding.rvJVS.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = LastJVSAdapter(getBaseActivity(), mViewModel, visitStatus)
        mBinding.rvJVS.adapter = adapter
        mViewModel.getSourceList().observe(viewLifecycleOwner, Observer { adapter.updateList(it) })
        mViewModel.populateData()
    }

    private fun showDateDialog(id: Int) {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    when (id) {
                        INDUS_DATE_OF_COLLECTION -> mBinding.edtIndusDateOfCollection.setText("$year-${month + 1}-$dayOfMonth")
                        INDUS_DATE -> mBinding.edtDateIndus.setText("$year-${month + 1}-$dayOfMonth")
                        DOMESTIC_DATE_OF_COLLECTION -> mBinding.edtDomesticDateOfCollection.setText(
                            "$year-${month + 1}-$dayOfMonth"
                        )
                        DOMESTIC_DATE -> mBinding.edtDateDomestic.setText("$year-${month + 1}-$dayOfMonth")
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    private fun setListener() {
        mBinding.run {

            //If visit Status is true, then do not enable these listeners as it will enable
            //the textviews.
            if (!visitStatus) {
                //Industrial Payment Details
                rgPymntDetailsIndus.setOnCheckedChangeListener { group, checkedId ->
                    report.data.routineReport.paymentDetailsIndus =
                        if (checkedId == R.id.rbPymntDetailsIndusYes) {
                            //Enable Amount & Date
                            edtAmtIndus.isEnabled = true
                            edtDateIndus.isEnabled = true
                            "0"
                        } else {
                            //Disable Amount & Date
                            edtAmtIndus.isEnabled = false
                            edtDateIndus.isEnabled = false

                            edtAmtIndus.setText("")
                            edtDateIndus.setText("")
                            "1"
                        }
                }

                //Domestic Payment Details
                rgPymntDetailsDomestic.setOnCheckedChangeListener { group, checkedId ->
                    report.data.routineReport.paymentDetailsDomestic =
                        if (checkedId == R.id.rbPymntDetailsDomesticYes) {
                            //Enable Amount & Date
                            edtAmtDomestic.isEnabled = true
                            edtDateDomestic.isEnabled = true
                            "0"
                        } else {
                            //Disable Amount & Date
                            edtAmtDomestic.isEnabled = false
                            edtDateDomestic.isEnabled = false

                            edtAmtDomestic.setText("")
                            edtDateDomestic.setText("")
                            "1"
                        }
                }
            }

            //JVS Sample
            rgJVSSample.setOnCheckedChangeListener { group, checkedId ->
                report.data.routineReport.jvsSampleCollectedForWater =
                    if (checkedId == R.id.rbJVSSampleYes) {
                        rvJVS.visibility = View.VISIBLE

                        if (!visitStatus){
                            tvAddMore.visibility = View.VISIBLE
                            imgDelete.visibility = View.VISIBLE
                        }
                        1
                    } else {
                        rvJVS.visibility = View.GONE

                        if (!visitStatus) {
                            tvAddMore.visibility = View.GONE
                            imgDelete.visibility = View.GONE
                        }
                        0
                    }
            }
        }
    }

    private fun onSubmit() {
        report.data.routineReport.dateOfCollectionIndus =
            mBinding.edtIndusDateOfCollection.text.toString()
        report.data.routineReport.paymentDetailsIndusAmount = mBinding.edtAmtIndus.text.toString()
        report.data.routineReport.paymentDetailsIndusDate = mBinding.edtDateIndus.text.toString()

        report.data.routineReport.dateOfCollectionDomestic =
            mBinding.edtDomesticDateOfCollection.text.toString()
        report.data.routineReport.paymentDetailsDomesticAmount =
            mBinding.edtAmtDomestic.text.toString()
        report.data.routineReport.paymentDetailsDomesticDate =
            mBinding.edtDateDomestic.text.toString()

        if (report.data.routineReport.jvsSampleCollectedForWater == 1) {
            report.data.jvsSampleCollectedWaterSource = mViewModel.getReportData()
        }else {
            report.data.jvsSampleCollectedWaterSource = arrayListOf()
        }

        if (validate()) {
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_8,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_9, visitReportId)
        }
    }

    private fun validate(): Boolean {
        //Indus Date of Collection
        if (mBinding.edtIndusDateOfCollection.text.isNullOrEmpty()) {
            showMessage("Enter Date of collection")
            return false
        }

        //Indus Payment Details
        if (!mBinding.rbPymntDetailsIndusYes.isChecked && !mBinding.rbPymntDetailsIndusNo.isChecked) {
            showMessage("Select Payment Details")
            return false
        }

        if (mBinding.rbPymntDetailsIndusYes.isChecked){
            //Indus Amount
            if (mBinding.edtAmtIndus.text.isNullOrEmpty()) {
                showMessage("Enter Amount")
                return false
            }else if (!isDecimal(mBinding.edtAmtIndus.text.toString())){
                showMessage("Invalid Industrial Amount")
                return false
            }

            //Indus Date
            if (mBinding.edtDateIndus.text.isNullOrEmpty()) {
                showMessage("Enter Date")
                return false
            }
        }

        //Domestic Date of Collection
        if (mBinding.edtDomesticDateOfCollection.text.isNullOrEmpty()) {
            showMessage("Enter Date of collection")
            return false
        }

        //Domestic Payment Details
        if (!mBinding.rbPymntDetailsDomesticYes.isChecked && !mBinding.rbPymntDetailsDomesticNo.isChecked) {
            showMessage("Select Payment Details")
            return false
        }

        if (mBinding.rbPymntDetailsDomesticYes.isChecked){
            //Domestic Amount
            if (mBinding.edtAmtDomestic.text.isNullOrEmpty()) {
                showMessage("Enter Amount")
                return false
            }else if (!isDecimal(mBinding.edtAmtDomestic.text.toString())){
                showMessage("Invalid Domestic Amount")
                return false
            }

            //Domestic Date
            if (mBinding.edtDateDomestic.text.isNullOrEmpty()) {
                showMessage("Enter Date")
                return false
            }
        }

        //JVS Sample Collected for water
        if (!mBinding.rbJVSSampleYes.isChecked && !mBinding.rbJVSSampleNo.isChecked) {
            showMessage("Select JVS Sample")
            return false
        }

        var isValid = true

        //Validation for JVS Views
        if (mBinding.rbJVSSampleYes.isChecked) {
            val sampleList = mViewModel.getReportData()
            outer@ for (item in sampleList) {
                if (item.nameOfSource.isEmpty()) {
                    showMessage("Enter Source")
                    isValid = false
                    break
                }
                for (childItem in item.lastJvsChild) {
                    if (childItem.prescribedValue.isEmpty()) {
                        showMessage("Enter Prescribed Value")
                        isValid = false
                        break@outer
                    }
//                    else if (!isDecimal(childItem.prescribedValue)){
//                        showMessage("Invalid Prescribed value.")
//                        isValid = false
//                        break@outer
//                    }
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
                    //Industrial Date
                    edtIndusDateOfCollection.setText(dateOfCollectionIndus)

                    //Payment Details
                    if (paymentDetailsIndus != "1"){
                        rgPymntDetailsIndus.check(R.id.rbPymntDetailsIndusYes)
                    }else{
                        rgPymntDetailsIndus.check(R.id.rbPymntDetailsIndusNo)
                    }

                    //Payment Details Amount
                    edtAmtIndus.setText(paymentDetailsIndusAmount)

                    //Payment Details Date
                    edtDateIndus.setText(paymentDetailsIndusDate)


                    //Domestic Date
                    edtDomesticDateOfCollection.setText(dateOfCollectionDomestic)

                    //Domestic Payment Details
                    if (paymentDetailsDomestic != "1"){
                        rgPymntDetailsDomestic.check(R.id.rbPymntDetailsDomesticYes)
                    }else{
                        rgPymntDetailsDomestic.check(R.id.rbPymntDetailsDomesticNo)
                    }

                    //Domestic Payment Details Amount
                    edtAmtDomestic.setText(paymentDetailsDomesticAmount)

                    //Domestic Payment Details Date
                    edtDateDomestic.setText(paymentDetailsDomesticDate)

                    //JVS Sample Collected
                    if (jvsSampleCollectedForWater == 1)
                        rgJVSSample.check(R.id.rbJVSSampleYes)
                    else
                        rgJVSSample.check(R.id.rbJVSSampleNo)

                    //JVS Data
                    if (reports?.data?.jvsSampleCollectedWaterSource != null)
                        mViewModel.populateData(reports?.data?.jvsSampleCollectedWaterSource)

                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }

}

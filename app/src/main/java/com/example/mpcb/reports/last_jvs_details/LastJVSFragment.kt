package com.example.mpcb.reports.last_jvs_details


import android.app.DatePickerDialog
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentLastJvsBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import java.util.*

class LastJVSFragment : BaseFragment<FragmentLastJvsBinding, LastJVSViewModel>(), LastJVSNavigator {

    private val INDUS_DATE_OF_COLLECTION = 1
    private val INDUS_DATE = 2
    private val DOMESTIC_DATE_OF_COLLECTION = 3
    private val DOMESTIC_DATE = 4

    override fun getLayoutId() = R.layout.fragment_last_jvs
    override fun getViewModel() = LastJVSViewModel::class.java
    override fun getNavigator() = this@LastJVSFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_8)
        setUpRecyclerView()
        setListener()

        mBinding.edtIndusDateOfCollection.setOnClickListener {
            showDateDialog(INDUS_DATE_OF_COLLECTION)
        }
        mBinding.edtDateIndus.setOnClickListener { showDateDialog(INDUS_DATE) }
        mBinding.edtDomesticDateOfCollection.setOnClickListener {
            showDateDialog(DOMESTIC_DATE_OF_COLLECTION)
        }
        mBinding.edtDateDomestic.setOnClickListener { showDateDialog(DOMESTIC_DATE) }

        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.btnSubmit.setOnClickListener { onSubmit() }

    }

    private fun setUpRecyclerView() {
        mBinding.rvJVS.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = LastJVSAdapter(getBaseActivity(), mViewModel)
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
        mBinding.rgPymntDetailsIndus.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.paymentDetailsIndus =
                if (checkedId == R.id.rbPymntDetailsIndusYes) "1" else "0"
        }

        mBinding.rgPymntDetailsDomestic.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.paymentDetailsDomestic =
                if (checkedId == R.id.rbPymntDetailsDomesticYes) "1" else "0"
        }

        mBinding.rgJVSSample.setOnCheckedChangeListener { group, checkedId ->
            report.data.routineReport.jvsSampleCollectedForWater =
                if (checkedId == R.id.rbJVSSampleYes) {
                    mBinding.rvJVS.visibility = View.VISIBLE
                    mBinding.txtAddMore.visibility = View.VISIBLE
                    1
                } else {
                    mBinding.rvJVS.visibility = View.GONE
                    mBinding.txtAddMore.visibility = View.GONE
                    0
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
        }
        saveReportData()
        addReportFragment(Constants.REPORT_9)
    }


}

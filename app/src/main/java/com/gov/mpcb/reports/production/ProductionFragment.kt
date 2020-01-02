package com.gov.mpcb.reports.production


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentProductionBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.ReportsPageActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.utils.validations.isDecimal

class ProductionFragment : BaseFragment<FragmentProductionBinding, ProductionViewModel>(),
    ProductionNavigator {

    lateinit var adapter: ProductionAdapter
    private var reports: ReportRequest? = null
    private lateinit var visitReportId: String

    override fun getLayoutId() = R.layout.fragment_production
    override fun getViewModel() = ProductionViewModel::class.java
    override fun getNavigator() = this@ProductionFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //If true, disable all controls!
        disableViews(mBinding.categoryParentLay)

        //Method to Show or Hide Save & Next Button
        showNextButton(mBinding.btnSaveNext)

        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_2)

        //Get Visit Report ID from arguments
        visitReportId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)

        //set report variable data
        setReportVariableData(visitReportId)

        setUpRecyclerView()
        mBinding.run{
            tvAddMore.setOnClickListener { mViewModel.addItem() }
            imgDelete.setOnClickListener { mViewModel.deleteItem() }
            btnSaveNext.run {
                btnSubmit.setOnClickListener { onSubmit() }
                btnNext.setOnClickListener{ addReportFragmentLocal(Constants.REPORT_3, visitReportId) }
            }
        }
    }

    private fun setUpRecyclerView() {
        adapter = ProductionAdapter(getBaseActivity(), mViewModel, visitStatus)
        mBinding.rvProduction.layoutManager =
            LinearLayoutManager(getBaseActivity().applicationContext)
        mBinding.rvProduction.adapter = adapter
        mViewModel.getProductList().observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
        mViewModel.populateData()
    }

    private fun onSubmit() {
        if (validateFieldsFilled()) {
            report.data.routineReportProducts.clear()
            report.data.routineReportProducts = mViewModel.getProductList().value!!
            saveReportData(
                reportNo = visitReportId,
                reportKey = Constants.REPORT_2,
                reportStatus = true
            )
            //Put the Visit Report ID in bundle to share to Fragments
            addReportFragmentLocal(Constants.REPORT_3, visitReportId)
        }
    }


    /**
     * Method to validate if fields of report form are filled.
     */
    private fun validateFieldsFilled(): Boolean {
        var isValid = true
        val productList = mViewModel.getProductList().value!!
        for (item in productList) {
            if (item.productName.isEmpty()) {
                showMessage("Enter Product Name")
                isValid = false
                break
            }
            if (item.productQuantity.isEmpty()) {
                showMessage("Enter Product Concent Quantity")
                isValid = false
                break
            }else if (!isDecimal(item.productQuantity)){
                //Check if correct decimal value
                showMessage("Invalid Product Concent Quantity")
                isValid = false
                break
            }
            if (item.productUom == "0") {
                showMessage("Select Unit As Concent")
                isValid = false
                break
            }
            if (item.productQuantityActual.isEmpty()) {
                showMessage("Enter Product Actual Quantity")
                isValid = false
                break
            }else if (!isDecimal(item.productQuantityActual)){
                //Check if correct decimal value
                showMessage("Invalid Product Actual Quantity")
                isValid = false
                break
            }
            if (item.productUomActual == "0") {
                showMessage("Select Unit As Actual")
                isValid = false
                break
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

        if(reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportProducts)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }

}

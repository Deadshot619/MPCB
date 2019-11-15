package com.example.mpcb.reports.production


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentProductionBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class ProductionFragment : BaseFragment<FragmentProductionBinding, ProductionViewModel>(),
    ProductionNavigator {

    lateinit var adapter: ProductionAdapter
    private var reports: ReportRequest? = null

    override fun getLayoutId() = R.layout.fragment_production
    override fun getViewModel() = ProductionViewModel::class.java
    override fun getNavigator() = this@ProductionFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_2)
        setUpRecyclerView()

        mBinding.txtAddMore.setOnClickListener { mViewModel.addItem() }
        mBinding.imgDelete.setOnClickListener { mViewModel.deleteItem() }
        mBinding.btnSubmit.setOnClickListener { onSubmit() }
    }

    private fun setUpRecyclerView() {
        adapter = ProductionAdapter(getBaseActivity(), mViewModel)
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
                reportKey = Constants.REPORT_2,
                reportStatus = true
            )
            addReportFragment(Constants.REPORT_3)
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
        reports = getReportData()
        if(reports?.data?.routineReportProducts != null)
            mViewModel.populateData(reports?.data?.routineReportProducts!!)
    }

    override fun onStart() {
        super.onStart()
        setDataToViews()
    }

}

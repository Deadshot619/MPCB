package com.example.mpcb.visit_report


import android.view.View
import android.widget.Toast
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentVisitReportBinding
import com.example.mpcb.utils.showMessage


class VisitReportFragment : BaseFragment<FragmentVisitReportBinding, VisitReportViewModel>(), VisitReportNavigator {

    override fun getLayoutId() = R.layout.fragment_visit_report
    override fun getViewModel() = VisitReportViewModel::class.java
    override fun getNavigator() = this@VisitReportFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.toolbarLayout.visitId.text = "#32133232"
        mBinding.toolbarLayout.visitName.text = "Johnson Controls-Hitachi Air Conditioning India Ltd"


    }

}



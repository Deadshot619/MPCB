package com.example.mpcb.reports.non_hazardous_waste_management

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentNonHazardiousBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class NonHazardousFragment : BaseFragment<FragmentNonHazardiousBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    val reportsItems: ArrayList<String> = ArrayList()
    override fun getLayoutId() = R.layout.fragment_non_hazardious
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@NonHazardousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_13)


        mBinding.btnSubmit.setOnClickListener {
            addReportFragment(Constants.REPORT_14)
        }

        mBinding.rvNonHazardous.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        val adapter = NonHazardousAdapter(getBaseActivity(), mViewModel)
        mBinding.rvNonHazardous.adapter = adapter
    }


}
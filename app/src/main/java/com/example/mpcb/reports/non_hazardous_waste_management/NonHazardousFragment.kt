package com.example.mpcb.reports.non_hazardous_waste_management

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentHazardiousBinding
import com.example.mpcb.databinding.FragmentNonHazardiousBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.reports.hazardous_waste_management.CustomAdapter
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage
import kotlinx.android.synthetic.main.fragment_hazardious.*

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

        addReports()
        reports.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        reports.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        reports.adapter = CustomAdapter(reportsItems, getBaseActivity().applicationContext)
    }


    fun addReports() {
        reportsItems.add("Reports 1")
        reportsItems.add("Reports 2")
        reportsItems.add("Reports 3")
        reportsItems.add("Reports 4")
    }
}
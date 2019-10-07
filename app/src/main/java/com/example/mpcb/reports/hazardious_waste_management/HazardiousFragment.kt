package com.example.mpcb.reports.hazardious_waste_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAirPollutionBinding
import com.example.mpcb.databinding.FragmentHazardiousBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage
import kotlinx.android.synthetic.main.fragment_hazardious.*

class HazardiousFragment: BaseFragment<FragmentHazardiousBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    val reportsItems: ArrayList<String> = ArrayList()
    override fun getLayoutId() = R.layout.fragment_hazardious
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@HazardiousFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {
    addReports()
    reports.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        reports.layoutManager = LinearLayoutManager(getBaseActivity().applicationContext)
        reports.adapter = CustomAdapter(reportsItems, getBaseActivity().applicationContext)
    }


    fun addReports(){
        reportsItems.add("Reports 1")
        reportsItems.add("Reports 2")
        reportsItems.add("Reports 3")
        reportsItems.add("Reports 4")
    }
}
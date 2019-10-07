package com.example.mpcb.reports.tree_plantation


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTreePlantationBinding
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.showMessage


class TreePlantationFragment : BaseFragment<FragmentTreePlantationBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {

    override fun getLayoutId() = R.layout.fragment_tree_plantation
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreePlantationFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
    }


}

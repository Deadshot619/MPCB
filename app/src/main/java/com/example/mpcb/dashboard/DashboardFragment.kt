package com.example.mpcb.dashboard


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDashboardBinding
import com.example.mpcb.utils.showMessage


class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(), DashboardNavigator {

    override fun getLayoutId() = R.layout.fragment_dashboard
    override fun getViewModel() = DashboardViewModel::class.java
    override fun getNavigator() = this@DashboardFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.dashboardModel = mViewModel.getDashboardModel()
        setToolbar(mBinding.toolbarLayout, "Dashboard")
        mViewModel.getDashboardData()
    }

}

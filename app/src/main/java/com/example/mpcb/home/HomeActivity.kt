package com.example.mpcb.home

import android.content.Context
import android.content.Intent
import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.dashboard.DashboardFragment
import com.example.mpcb.databinding.ActivityHomeBinding
import com.example.mpcb.profile.ProfileFragment
import com.example.mpcb.reports.industry.IndustryReportFragment
import com.example.mpcb.reports.oms_stack.OMSStackFragment
import com.example.mpcb.utils.showMessage
import com.example.mpcb.visit_report.VisitReportFragment

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {

    override fun getLayoutId() = R.layout.activity_home
    override fun getViewModel() = HomeViewModel::class.java
    override fun getNavigator() = this@HomeActivity
    override fun onInternetError() {}
    override fun onError(message: String) = showMessage(message)

    companion object {
        fun getCallingIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    override fun onBinding() {
        setListeners()
        mBinding.bottomNavigation.menu.performIdentifierAction(R.id.dashboard, 2)
        mBinding.bottomNavigation.menu.getItem(2).setChecked(true)
    }

    private fun setListeners() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.task_mngmt -> {
                    showMessage("Task Management")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.my_visits -> {
                    addFragment(VisitReportFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.dashboard -> {
                    addFragment(DashboardFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.attendance -> {
                    showMessage("Attendance")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.my_profile -> {
                    addFragment(IndustryReportFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

}

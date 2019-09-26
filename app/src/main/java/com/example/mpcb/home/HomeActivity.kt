package com.example.mpcb.home

import com.example.mpcb.R
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.dashboard.DashboardFragment
import com.example.mpcb.databinding.ActivityHomeBinding
import com.example.mpcb.profile.ProfileFragment
import com.example.mpcb.reports.industry.IndustryReportFragment
import com.example.mpcb.utils.showMessage

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeNavigator {

    override fun getLayoutId() = R.layout.activity_home
    override fun getViewModel() = HomeViewModel::class.java
    override fun getNavigator() = this@HomeActivity
    override fun onInternetError() {}
    override fun onError(message: String) = showMessage(message)

    override fun onBinding() {
        setListeners()

    }

    private fun setListeners() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragmentOne -> {
                    supportFragmentManager.beginTransaction().add(R.id.container, ProfileFragment()).addToBackStack(null).commit()
                    showMessage("One")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragmentTwo -> {
                    showMessage("Two")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragmentThree -> {
                    supportFragmentManager.beginTransaction().add(R.id.container, DashboardFragment()).addToBackStack(null).commit()
                    showMessage("Three")
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragmentFour -> {
                    showMessage("four")
                    supportFragmentManager.beginTransaction().add(R.id.container, IndustryReportFragment()).addToBackStack(null).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.fragmentFive -> {
                    showMessage("five")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

}

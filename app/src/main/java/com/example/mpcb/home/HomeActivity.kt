package com.example.mpcb.home

import android.content.Context
import android.content.Intent
import com.example.mpcb.R
import com.example.mpcb.addTask.AddTaskFragment
import com.example.mpcb.attendance.AttendanceFragment
import com.example.mpcb.base.BaseActivity
import com.example.mpcb.dashboard.DashboardFragment
import com.example.mpcb.databinding.ActivityHomeBinding
import com.example.mpcb.my_visits.MyVisitsFragment
import com.example.mpcb.profile.ProfileFragment
import com.example.mpcb.task_management.TaskManagementFragment
import com.example.mpcb.utils.showMessage

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
        mBinding.bottomNavigation.menu.getItem(2).isChecked = true

    }

    private fun setListeners() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.task_mngmt -> {
                    addFragment(TaskManagementFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.my_visits -> {
                    addFragment(MyVisitsFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.dashboard -> {
                    addFragment(DashboardFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.attendance -> {
                    addFragment(AttendanceFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.my_profile -> {
                    addFragment(ProfileFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is AddTaskFragment) {
            supportFragmentManager.beginTransaction().remove(currentFragment).commit()
        } else {
            super.onBackPressed()
        }
    }

}

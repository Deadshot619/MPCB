package com.gov.mpcb.home

import android.content.Context
import android.content.Intent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gov.mpcb.R
import com.gov.mpcb.addTask.AddTaskFragment
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.dashboard.DashboardFragment
import com.gov.mpcb.databinding.ActivityHomeBinding
import com.gov.mpcb.menu.MenuFragment
import com.gov.mpcb.my_visits.MyVisitsFragment
import com.gov.mpcb.profile.ProfileFragment
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import com.gov.mpcb.utils.showMessage

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

//        showPopUp()
    }

    //TODO 31/12/2019 Remove these toast message once the implementation of new features is completed.
    private fun setListeners() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
               /* R.id.task_mngmt -> {
                    replaceFragment(TaskManagementFragment(), false)
//                    showMessage(getString(R.string.error_bottom_nav))
                    return@setOnNavigationItemSelectedListener true
                }*/
                R.id.my_visits -> {
                    replaceFragment(MyVisitsFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.dashboard -> {
                    replaceFragment(DashboardFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.attendance -> {
//                    replaceFragment(AttendanceFragment(), false)
                    showMessage(getString(R.string.error_bottom_nav))
                    return@setOnNavigationItemSelectedListener false
                }
                R.id.my_profile -> {
                    replaceFragment(ProfileFragment(), false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu -> {
                    replaceFragment(MenuFragment(), false)
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

    //This method will be used to display a popup message when the app is opened for first time
    private fun showPopUp(){
        val isFirstTime = PreferencesHelper.getAppLaunchFirstTime()

        if (isFirstTime){
            MaterialAlertDialogBuilder(this, R.style.CustomMaterialDialogStyle)
                .setTitle("Attention!")
                .setMessage("It has been instructed not to carry out the randomized inspection till further order from Respected Member Secretary Sir.")
                .setCancelable(false)
                .setPositiveButton("OK"){dialog,_ ->  dialog.dismiss()}
                .show()

            //Indicates that the app is launched
            PreferencesHelper.setAppLaunchFirstTime(false)
        }
    }
}

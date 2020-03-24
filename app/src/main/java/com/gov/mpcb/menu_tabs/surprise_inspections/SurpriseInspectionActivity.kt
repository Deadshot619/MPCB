package com.gov.mpcb.menu_tabs.surprise_inspections

import android.content.Intent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivitySurpriseInspectionBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.add_surprise_inspection.AddSurpriseInspectionActivity
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class SurpriseInspectionActivity : BaseActivity<ActivitySurpriseInspectionBinding, SurpriseInspectionsViewModel>(),
SurpriseInspectionsNavigator{

    //This variable will be used to determine whether or not to reload this fragment
    private var RELOAD = ""

    override fun getLayoutId() = R.layout.activity_surprise_inspection
    override fun getViewModel() = SurpriseInspectionsViewModel::class.java
    override fun getNavigator() = this@SurpriseInspectionActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    private lateinit var mAdapter : SurpriseInspectionPagerAdapter

    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.surprise_inspections),
            showSearchBar = false,
            showCalendar = false,
            showBackButton = true
        )

        setUpViewpager(viewPager = mBinding.viewpager)

        setUpTabLayoutMediator(tabLayout = mBinding.tabLayout, viewPager = mBinding.viewpager)

        setUpListeners()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        /**
         * This method is called when activity is brought back to life.
         * This checks if the intent has "reload_value" in intent, if yes, it resets the ViewPager adapter.
         */
        RELOAD = intent?.getStringExtra(Constants.RELOAD_KEY) ?: ""

        if (RELOAD == Constants.RELOAD_VALUE)
            setUpViewpager(mBinding.viewpager)
    }

    /**
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            finish()
        }

        //Start [AddSurpriseInspectionActivity] on click of FAB
        mBinding.fabSurpriseInspection.setOnClickListener {
            startActivity(Intent(this, AddSurpriseInspectionActivity::class.java))
        }
    }

    /**
     * This method is used to setup ViewPager with [SurpriseInspectionPagerAdapter]
     */
    private fun setUpViewpager(viewPager: ViewPager2){
        mAdapter = SurpriseInspectionPagerAdapter(this)
        viewPager.adapter = mAdapter /*SurpriseInspectionPagerAdapter(this)*/
    }

    /**
     * This method sets/links up the Tab Layout with ViewPager using [TabLayoutMediator]
     */
    private fun setUpTabLayoutMediator(tabLayout: TabLayout, viewPager: ViewPager2){
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.applied_by_me)
                1 -> tab.text = getString(R.string.verified_surprise_inspections)
            }
        }.attach()
    }
}

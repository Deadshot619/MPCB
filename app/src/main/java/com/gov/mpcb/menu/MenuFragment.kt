package com.gov.mpcb.menu

import android.content.Intent
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentMenuBinding
import com.gov.mpcb.menu_tabs.circulars.CircularsActivity
import com.gov.mpcb.menu_tabs.faq.FaqActivity
import com.gov.mpcb.menu_tabs.industry_directory.IndustryDirectoryActivity
import com.gov.mpcb.menu_tabs.surprise_inspections.SurpriseInspectionActivity
import com.gov.mpcb.utils.constants.Constants.Companion.setToolbar
import com.gov.mpcb.utils.showMessage

class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(),
    MenuNavigator {

    override fun getLayoutId() = R.layout.fragment_menu
    override fun getViewModel() = MenuViewModel::class.java
    override fun getNavigator() = this@MenuFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        //Set toolbar
        setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.title_menu),
            showSearchBar = false,
            showCalendar = false
        )

        setUpListeners()
    }

    /**
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.run {
            //Circulars Activity
            circulars.setOnClickListener {
                startActivity(Intent(activity, CircularsActivity::class.java))
            }

            //Surprise Inspections Activity
            surpriseInspections.setOnClickListener {
                startActivity(Intent(activity, SurpriseInspectionActivity::class.java))
            }

            //Industry Directory Activity
            industryDirectory.setOnClickListener {
                startActivity(Intent(activity, IndustryDirectoryActivity::class.java))
            }

            //FAQ Activity
            faq.setOnClickListener {
                startActivity(Intent(activity, FaqActivity::class.java))
            }
        }
    }
}

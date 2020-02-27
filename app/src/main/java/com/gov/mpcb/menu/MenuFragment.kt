package com.gov.mpcb.menu

import android.content.Intent
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentMenuBinding
import com.gov.mpcb.menu_tabs.faq.FaqActivity
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

    private fun setUpListeners() {
        mBinding.faq.setOnClickListener {
            startActivity(Intent(activity, FaqActivity::class.java))
        }
    }
}

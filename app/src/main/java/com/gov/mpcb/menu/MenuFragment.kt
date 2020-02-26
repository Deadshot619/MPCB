package com.gov.mpcb.menu

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentMenuBinding
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
    }
}

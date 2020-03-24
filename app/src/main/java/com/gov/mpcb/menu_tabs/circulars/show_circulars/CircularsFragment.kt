package com.gov.mpcb.menu_tabs.circulars.show_circulars

import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentCircularsBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CircularsFragment : BaseFragment<FragmentCircularsBinding, CircularsFragmentViewModel>(),
    CircularsFragmentNavigator {

    override fun getLayoutId() = R.layout.fragment_circulars
    override fun getViewModel() = CircularsFragmentViewModel::class.java
    override fun getNavigator() = this@CircularsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.circulars),
            showSearchBar = true,
            showCalendar = false,
            showBackButton = true
        )

        setListeners()
    }

    private fun setListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.finish()
        }
    }
}

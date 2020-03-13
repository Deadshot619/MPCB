package com.gov.mpcb.menu_tabs.surprise_inspections.industry_list

import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIndustryListBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class IndustryListFragment : BaseFragment<FragmentIndustryListBinding, IndustryListViewModel>(),
IndustryListNavigator{
    override fun getLayoutId() = R.layout.fragment_industry_list
    override fun getViewModel() = IndustryListViewModel::class.java
    override fun getNavigator() = this@IndustryListFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {

        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.industry_list),
            showSearchBar = true,
            showCalendar = false,
            showBackButton = true
        )

        setListeners()
    }

    /**
     * Method to set all types of listeners for this fragment
     */
    private fun setListeners() {
        //Destroy the activity on click of back button
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.finish()
        }
    }
}
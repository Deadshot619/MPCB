package com.gov.mpcb.menu_tabs.industry_directory.id_industry_list

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdIndustryListBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class IdIndustryListFragment : BaseFragment<FragmentIdIndustryListBinding, IdIndustryListViewModel>(), IdIndustryListNavigator {

    override fun getLayoutId() = R.layout.fragment_id_industry_list
    override fun getViewModel() = IdIndustryListViewModel::class.java
    override fun getNavigator() = this@IdIndustryListFragment
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

        setUpListeners()
    }

    /**
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.finish()
        }
    }


}

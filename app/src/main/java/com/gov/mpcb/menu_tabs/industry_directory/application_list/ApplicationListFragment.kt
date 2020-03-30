package com.gov.mpcb.menu_tabs.industry_directory.application_list

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentApplicationListBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class ApplicationListFragment :
    BaseFragment<FragmentApplicationListBinding, ApplicationListViewModel>(),
    ApplicationListNavigator {


    override fun getLayoutId() = R.layout.fragment_application_list
    override fun getViewModel() = ApplicationListViewModel::class.java
    override fun getNavigator() = this@ApplicationListFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.application_list),
            showSearchBar = false,
            showCalendar = false,
            showBackButton = true
        )

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        setUpListeners()
//
//        setUpRecyclerView(mBinding.rvIndustryList)
//
//
//        setObservers()
    }

    /**
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
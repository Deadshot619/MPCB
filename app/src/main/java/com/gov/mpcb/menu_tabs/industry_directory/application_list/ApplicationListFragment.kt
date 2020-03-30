package com.gov.mpcb.menu_tabs.industry_directory.application_list

import androidx.lifecycle.Observer
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentApplicationListBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class ApplicationListFragment :
    BaseFragment<FragmentApplicationListBinding, ApplicationListViewModel>(),
    ApplicationListNavigator {

    private var industryId: Int = -1

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

        //get industry id data from bundle
        industryId = arguments?.getInt(Constants.INDUSTRY_ID) ?: -1

        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }


        setUpListeners()
//
//        setUpRecyclerView(mBinding.rvIndustryList)
//
//
        setObservers()

        mViewModel.getIndustryData(industryId = industryId)
    }

    /**
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    /**
     * Method to setup Observers on this fragment
     */
    private fun setObservers() {
        mViewModel._viewIndustryData.observe(viewLifecycleOwner, Observer {
            it?.let {
                mBinding.layoutIdApplicationList.data = it
            }
        })
    }
}
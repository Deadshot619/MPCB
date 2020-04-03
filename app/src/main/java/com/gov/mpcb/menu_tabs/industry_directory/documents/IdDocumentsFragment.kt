package com.gov.mpcb.menu_tabs.industry_directory.documents

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdDocumentsBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class IdDocumentsFragment : BaseFragment<FragmentIdDocumentsBinding, IdDocumentsViewModel>(), IdDocumentsNavigator {

    override fun getLayoutId() = R.layout.fragment_id_documents
    override fun getViewModel() = IdDocumentsViewModel::class.java
    override fun getNavigator() = this@IdDocumentsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.documents),
            showSearchBar = false,
            showCalendar = false,
            showBackButton = true
        )

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

/*        setUpListeners()

        setUpRecyclerView(mBinding.rvIndustryList)


        setObservers()*/
    }
}

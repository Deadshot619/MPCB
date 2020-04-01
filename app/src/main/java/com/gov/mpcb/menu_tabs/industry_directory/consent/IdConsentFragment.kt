package com.gov.mpcb.menu_tabs.industry_directory.consent

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdConsentBinding
import com.gov.mpcb.menu_tabs.industry_directory.id_industry_list.IdConsentAdapter
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class IdConsentFragment : BaseFragment<FragmentIdConsentBinding, IdConsentViewModel>(),
    IdConsentNavigator {

    private var industryId: Int = -1

    private lateinit var mAdapter: IdConsentAdapter

    override fun getLayoutId() = R.layout.fragment_id_consent
    override fun getViewModel() = IdConsentViewModel::class.java
    override fun getNavigator() = this@IdConsentFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }

        //get industry id data from bundle
        industryId = arguments?.getInt(Constants.INDUSTRY_ID) ?: -1

        setUpRecyclerView(mBinding.rvConsentList)

        //Call api with industry Id provided
        if(isNetworkAvailable())
            mViewModel.getIndustryData(industryId)
    }


    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = IdConsentAdapter()

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}
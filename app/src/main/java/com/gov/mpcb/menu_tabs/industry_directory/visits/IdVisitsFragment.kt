package com.gov.mpcb.menu_tabs.industry_directory.visits

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdVisitsBinding
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.showMessage

class IdVisitsFragment : BaseFragment<FragmentIdVisitsBinding, IdVisitsViewModel>(), IdVisitsNavigator {



    private var industryId: Int = -1
    private val industryDirectoryType = IndustryDirectoryType.Visits

    private lateinit var mAdapter: IdVisitsAdapter

    override fun getLayoutId() = R.layout.fragment_id_visits
    override fun getViewModel() = IdVisitsViewModel::class.java
    override fun getNavigator() = this@IdVisitsFragment
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
            mViewModel.getIndustryData(industryId, industryDirectoryType)
    }


    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = IdVisitsAdapter()

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }




}

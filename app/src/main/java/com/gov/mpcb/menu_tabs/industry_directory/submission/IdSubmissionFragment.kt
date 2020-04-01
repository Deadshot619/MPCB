package com.gov.mpcb.menu_tabs.industry_directory.submission

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdSubmissionBinding
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.showMessage

class IdSubmissionFragment : BaseFragment<FragmentIdSubmissionBinding, IdSubmissionViewModel>(), IdSubmissionNavigator {

    //Variable to hold industry ID
    private var industryId: Int = -1

    //Variable to hold Industry Type
    private val industryDirectoryType = IndustryDirectoryType.Submission

    private lateinit var mAdapter: IdSubmissionAdapter

    override fun getLayoutId() = R.layout.fragment_id_submission
    override fun getViewModel() = IdSubmissionViewModel::class.java
    override fun getNavigator() = this@IdSubmissionFragment
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
        mAdapter = IdSubmissionAdapter()

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}

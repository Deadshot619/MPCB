package com.gov.mpcb.menu_tabs.industry_directory.authorization

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdAuthorizationBinding
import com.gov.mpcb.network.response.IdAuthorizationData
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.showMessage

class IdAuthorizationFragment : BaseFragment<FragmentIdAuthorizationBinding, IdAuthorizationViewModel>(), IdAuthorizationNavigator {

    private var industryId: Int = -1
    private val industryDirectoryType = IndustryDirectoryType.Authorization

    private lateinit var mAdapter: IdAuthorizationAdapter

    override fun getLayoutId() = R.layout.fragment_id_authorization
    override fun getViewModel() = IdAuthorizationViewModel::class.java
    override fun getNavigator() = this@IdAuthorizationFragment
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
        mAdapter = IdAuthorizationAdapter(object : IdAuthorizationAdapter.OnClickListener {
            override fun onEyeClick(idAuthData: IdAuthorizationData) {
                if (idAuthData.view_link.isNotEmpty()) {
                    CommonUtils.redirectUserToBrowser(activity!!, idAuthData.view_link)
                }
            }

            override fun onReportClick() {
            }

        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}

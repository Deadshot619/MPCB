package com.gov.mpcb.menu_tabs.industry_directory.legal

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdLegalBinding
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.showMessage

class IdLegalFragment : BaseFragment<FragmentIdLegalBinding, IdLegalViewModel>(), IdLegalNavigator {


    private var industryId: Int = -1
    private val industryDirectoryType = IndustryDirectoryType.Legal

    private lateinit var mAdapter: IdLegalAdapter

    override fun getLayoutId() = R.layout.fragment_id_legal
    override fun getViewModel() = IdLegalViewModel::class.java
    override fun getNavigator() = this@IdLegalFragment
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
        mAdapter = IdLegalAdapter(IdLegalAdapter.OnClickListener{
            if (it.legal_doc_link.isNotEmpty()) {
                CommonUtils.redirectUserToBrowser(activity!!, it.legal_doc_link)
            } else {
                showMessage("No PDF to view")
            }
        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}

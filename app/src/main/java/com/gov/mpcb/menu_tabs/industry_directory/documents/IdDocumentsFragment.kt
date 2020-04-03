package com.gov.mpcb.menu_tabs.industry_directory.documents

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdDocumentsBinding
import com.gov.mpcb.network.response.IdConsentData
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class IdDocumentsFragment : BaseFragment<FragmentIdDocumentsBinding, IdDocumentsViewModel>(), IdDocumentsNavigator {

    private lateinit var mAdapter: IdConsentDocumentsAdapter

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

        //Get applicant id from bundle
        val idConsentData: IdConsentData? = arguments?.getParcelable(Constants.IS_CONSENT_DATA_KEY)

        setUpRecyclerView(mBinding.rvList)
/*        setUpListeners()


        setObservers()*/

        idConsentData?.let {
            mViewModel.getConsentDocumentsData(applicantId = it.applicant_id)
            mBinding.tvTitle.text = it.industryname
        }
    }

    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = IdConsentDocumentsAdapter(IdConsentDocumentsAdapter.OnClickListener {
            CommonUtils.redirectUserToBrowser(activity!!, it.view_link)
        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}

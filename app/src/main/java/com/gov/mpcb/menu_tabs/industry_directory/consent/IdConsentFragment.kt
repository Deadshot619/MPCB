package com.gov.mpcb.menu_tabs.industry_directory.consent

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdConsentBinding
import com.gov.mpcb.menu_tabs.industry_directory.documents.IdDocumentsFragment
import com.gov.mpcb.menu_tabs.industry_directory.id_industry_list.IdConsentAdapter
import com.gov.mpcb.network.response.IdConsentData
import com.gov.mpcb.utils.*
import com.gov.mpcb.utils.constants.Constants

/**
 * A simple [Fragment] subclass.
 */
class IdConsentFragment : BaseFragment<FragmentIdConsentBinding, IdConsentViewModel>(),
    IdConsentNavigator {

    private var industryId: Int = -1
    private val industryDirectoryType = IndustryDirectoryType.Consent


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
        if (isNetworkAvailable())
            mViewModel.getIndustryData(industryId, industryDirectoryType)
    }


    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = IdConsentAdapter(object : IdConsentAdapter.OnClickListener {
            override fun onEyeClick(idConsentData: IdConsentData) {
                if (idConsentData.view_link.isNotEmpty()) {
                    CommonUtils.redirectUserToBrowser(activity!!, idConsentData.view_link)
                }
            }

            override fun onReportClick(idConsentData: IdConsentData) {
                replaceFragment(IdDocumentsFragment(), true, Bundle().apply {
                    putParcelable(Constants.IS_CONSENT_DATA_KEY, idConsentData)
                })
            }

        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}

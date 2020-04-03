package com.gov.mpcb.menu_tabs.industry_directory.submission

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdSubmissionBinding
import com.gov.mpcb.menu_tabs.industry_directory.documents.IdDocumentsFragment
import com.gov.mpcb.network.response.IdSubmissionData
import com.gov.mpcb.utils.*
import com.gov.mpcb.utils.constants.Constants

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
        mAdapter = IdSubmissionAdapter(object : IdSubmissionAdapter.OnClickListener {
            override fun onEyeClick(idSubmissionData: IdSubmissionData) {
                if (idSubmissionData.view_link.isNotEmpty()) {
                    CommonUtils.redirectUserToBrowser(activity!!, idSubmissionData.view_link)
                }
            }

            override fun onReportClick(idSubmissionData: IdSubmissionData) {
                replaceFragment(IdDocumentsFragment(), true, Bundle().apply {
                    putParcelable(Constants.IS_SUBM_DATA_KEY, idSubmissionData)
                    putBoolean(Constants.ID_OTHER_DOCUMENT_KEY, true)
                    putBoolean(Constants.IS_DATA_FOR_AUTH, false)
                })
            }

        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }
}

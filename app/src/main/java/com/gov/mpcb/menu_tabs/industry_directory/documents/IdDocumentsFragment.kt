package com.gov.mpcb.menu_tabs.industry_directory.documents

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdDocumentsBinding
import com.gov.mpcb.network.response.IdAuthorizationData
import com.gov.mpcb.network.response.IdConsentData
import com.gov.mpcb.network.response.IdSubmissionData
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

class IdDocumentsFragment : BaseFragment<FragmentIdDocumentsBinding, IdDocumentsViewModel>(),
    IdDocumentsNavigator {

    private val REQUEST_STORAGE_CODE = 100
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

        setUpRecyclerView(mBinding.rvList)
        setUpListeners()

        getDataFromBundleAndCallApi(arguments)

//        setObservers()

    }

    /**
     * This method will be used to set Title & Uan no. of the page which usually will be industry name
     */
    private fun setPageTitle(name: String, uanNo: String) {
        mBinding.run {
            tvTitle.text = name
            tvUanNo.text = uanNo
        }
    }

    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = IdConsentDocumentsAdapter(IdConsentDocumentsAdapter.OnClickListener {
            CommonUtils.checkPermissionAndDownloadPdf(
                context = activity!!,
                view = mBinding.root,
                url = it.view_link,
                fileName = it.Document_name
            )

//            CommonUtils.downloadPdf(
//                context = activity!!,
//                url = it.view_link,
//                fileName = it.Document_name
//            )

//            CommonUtils.downloadPdf(
//                context = activity!!,
//                url = it.view_link,
//                fileName = it.Document_name
//            )
        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
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
     * This method will be used to retrieve data from bundle and call APi accordingly
     */
    private fun getDataFromBundleAndCallApi(bundle: Bundle?) {
        //Get data from bundle to check whether data is from other documnet or not
        val isBundleFromOtherDocument =
            bundle?.getBoolean(Constants.ID_OTHER_DOCUMENT_KEY) ?: false

//       Check if the data is for Other Documents
        if (isBundleFromOtherDocument) {

            //Get data from bundle to check whether data is for Auth or for Subm
            val isDataForAuth = bundle?.getBoolean(Constants.IS_DATA_FOR_AUTH) ?: false

            //If true, then data is for AUTH
            if (isDataForAuth) {
                val idAuthorizationData =
                    bundle?.getParcelable<IdAuthorizationData?>(Constants.IS_AUTH_DATA_KEY)

                idAuthorizationData?.let {
                    mViewModel.getOtherDocumentsData(
                        applicantId = it.Application_id,
                        type = it.app_type
                    )
                    setPageTitle(name = it.industryname, uanNo = it.unique_id)
                }
            } else {            //If false, then data is SUBM
                val idSubmissionData =
                    bundle?.getParcelable<IdSubmissionData?>(Constants.IS_SUBM_DATA_KEY)

                idSubmissionData?.let {
                    mViewModel.getOtherDocumentsData(
                        applicantId = it.Application_id,
                        type = it.app_type
                    )
                    setPageTitle(name = it.industryname, uanNo = it.unique_id)

                }
            }

        } else { //If false, then data is for Consent
            //Get applicant id from bundle
            val idConsentData: IdConsentData? =
                bundle?.getParcelable(Constants.IS_CONSENT_DATA_KEY)

            idConsentData?.let {
                mViewModel.getConsentDocumentsData(applicantId = it.applicant_id)
                setPageTitle(name = it.industryname, uanNo = it.unique_id)
            }
        }

    }
}

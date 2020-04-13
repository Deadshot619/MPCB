package com.gov.mpcb.menu_tabs.industry_directory.bank_guarantee

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdBankGuaranteeBinding
import com.gov.mpcb.menu_tabs.industry_directory.bank_guarantee.IdBankGuaranteeAdapter.OnClickListener
import com.gov.mpcb.network.response.IdBankGuaranteeData
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.IndustryDirectoryType
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.isNetworkAvailable
import com.gov.mpcb.utils.showMessage

class IdBankGuaranteeFragment : BaseFragment<FragmentIdBankGuaranteeBinding, IdBankGuaranteeViewModel>(), IdBankGuaranteeNavigator {


    private var industryId: Int = -1
    private val industryDirectoryType = IndustryDirectoryType.BankGuarantee

    private lateinit var mAdapter: IdBankGuaranteeAdapter

    override fun getLayoutId() = R.layout.fragment_id_bank_guarantee
    override fun getViewModel() = IdBankGuaranteeViewModel::class.java
    override fun getNavigator() = this@IdBankGuaranteeFragment
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
        mAdapter = IdBankGuaranteeAdapter(object : OnClickListener {
            override fun onEyeClick(idBankGuaranteeData: IdBankGuaranteeData) {
                if (idBankGuaranteeData.view_link.isNotEmpty()) {
                    CommonUtils.redirectUserToBrowser(activity!!, idBankGuaranteeData.view_link)
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

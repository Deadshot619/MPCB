package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeAdapter.OnClickListener
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.network.response.ViewAppliedListResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class AppliedByMeFragment : BaseFragment<FragmentAppliedByMeBinding, AppliedByMeViewModel>(), AppliedByMeNavigator {

    private lateinit var mAdapter: AppliedByMeAdapter
    private var viewAppliedListData: List<ViewAppliedListData>? = null

    /**
     * if true, then the data is for Applied For Me section.
     * if false, then the data is for Verified Surprise Inspections section.
     */
    private var isDataForAppliedByMe: Boolean = true

    override fun getLayoutId() = R.layout.fragment_applied_by_me
    override fun getViewModel() = AppliedByMeViewModel::class.java
    override fun getNavigator() = this@AppliedByMeFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        viewAppliedListData =
            arguments?.getParcelable<ViewAppliedListResponse>(Constants.SI_DATA)?.data?.run { this }
        isDataForAppliedByMe = arguments?.getBoolean(Constants.ADDED_BY_ME) ?: true

        setUpRecyclerView(mBinding.rvListings)
    }

    /**
     * This method filters data for Verified Surprise Inspection section.
     * Pass value as true if the List passed is for [AppliedByMeFragment], otherwise false
     *
     * @param list List of [ViewAppliedListData]
     * @param dataForAppliedByMe Takes a Boolean as input
     */
    private fun filterData(
        list: List<ViewAppliedListData>,
        dataForAppliedByMe: Boolean
    ): List<ViewAppliedListData> {
        return if (dataForAppliedByMe)
            list.filter { it.is_approved_by_hod != 1 }
        else
            list.filter { it.is_approved_by_hod == 1 }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        mAdapter = AppliedByMeAdapter(
            OnClickListener {
                showMessage(it.industry_name)
            }
        )
        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, VERTICAL, false)
            this.adapter = mAdapter
        }

        viewAppliedListData?.let {
            if (isDataForAppliedByMe) {
                mAdapter.submitList(filterData(it, isDataForAppliedByMe))
//                showMessage("$isDataForAppliedByMe")
            } else {
                mAdapter.submitList(filterData(it, isDataForAppliedByMe))
//                showMessage("$isDataForAppliedByMe")
            }
        }
    }

}
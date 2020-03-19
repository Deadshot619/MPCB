package com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeFragment
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class VerifiedSurpriseInspectionsFragment : BaseFragment<FragmentAppliedByMeBinding, VerifiedSurpriseInspectionsViewModel>(),
VerifiedSurpriseInspectionsNavigator{

    private lateinit var mAdapter: AppliedByMeAdapter
    private var viewAppliedListData: List<ViewAppliedListData>? = null

    /**
     * if true, then the data is for Applied For Me section.
     * if false, then the data is for Verified Surprise Inspections section.
     */
    private var isDataForAppliedByMe: Boolean = true


    override fun getLayoutId() = R.layout.fragment_applied_by_me
    override fun getViewModel() = VerifiedSurpriseInspectionsViewModel::class.java
    override fun getNavigator() = this@VerifiedSurpriseInspectionsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        viewAppliedListData =
            arguments?.getParcelableArrayList<ViewAppliedListData>(Constants.SI_DATA)?.toList()

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
            AppliedByMeAdapter.OnClickListener {
                showMessage(it.industry_name)
            }
        )

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }

        viewAppliedListData?.let {
            var tempData = listOf<ViewAppliedListData>()

            if (isDataForAppliedByMe) {
                tempData = filterData(it, isDataForAppliedByMe)
                mAdapter.submitList(tempData)
//                showMessage("$isDataForAppliedByMe")
            } else {
                tempData = filterData(it, isDataForAppliedByMe)
                mAdapter.submitList(tempData)
//                showMessage("$isDataForAppliedByMe")
            }

            //Show error text if data is not present
            if (tempData.isNullOrEmpty())
                mBinding.tvErrorText.visibility = View.VISIBLE

        }
    }

}
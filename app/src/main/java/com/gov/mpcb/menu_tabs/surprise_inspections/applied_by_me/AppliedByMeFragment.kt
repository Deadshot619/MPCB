package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.network.response.ViewAppliedListResponse
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class AppliedByMeFragment : Fragment() {

    private lateinit var mBinding: FragmentAppliedByMeBinding
    private lateinit var mAdapter: AppliedByMeAdapter
    private var viewAppliedListData: List<ViewAppliedListData>? = null

    /**
     * if true, then the data is for Applied For Me section.
     * if false, then the data is for Verified Surprise Inspections section.
     */
    private var isDataForAppliedByMe: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applied_by_me, container, false)

        viewAppliedListData =
            arguments?.getParcelable<ViewAppliedListResponse>(Constants.SI_DATA)?.data?.run { this }
        isDataForAppliedByMe = arguments?.getBoolean(Constants.ADDED_BY_ME) ?: true

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView(mBinding.rvListings)
    }

    //This method filters data for Verified Surprise Inspection section
    private fun filterData(list: List<ViewAppliedListData>): List<ViewAppliedListData> {
        return list.filter { it.is_approved_by_hod == 1 }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        mAdapter = AppliedByMeAdapter()
        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, VERTICAL, false)
            this.adapter = mAdapter
        }

        viewAppliedListData?.let {
            if (isDataForAppliedByMe) {
                mAdapter.submitList(it)
                showMessage("$isDataForAppliedByMe")
            }
            else {
                mAdapter.submitList(filterData(it))
                showMessage("$isDataForAppliedByMe")
            }
        }
    }

}
package com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeAdapter
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.network.response.ViewAppliedListResponse

/**
 * A simple [Fragment] subclass.
 */
class VerifiedSurpriseInspectionsFragment : Fragment() {

    private lateinit var mBinding: FragmentAppliedByMeBinding
    private lateinit var mAdapter: AppliedByMeAdapter
    private var viewAppliedListData: List<ViewAppliedListData>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applied_by_me, container, false)

        viewAppliedListData =
            arguments?.getParcelable<ViewAppliedListResponse>("data")?.data?.run { this }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView(mBinding.rvListings)

    }

    private fun filterData(list: List<ViewAppliedListData>): List<ViewAppliedListData> {
        return list.filter { it.is_approved_by_hod == 1 }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        mAdapter = AppliedByMeAdapter()
        recyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = mAdapter
        viewAppliedListData?.let {
            mAdapter.submitList(filterData(it))
        }

    }

}
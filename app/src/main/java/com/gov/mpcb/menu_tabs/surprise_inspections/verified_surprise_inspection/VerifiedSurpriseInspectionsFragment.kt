package com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeViewModel
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class VerifiedSurpriseInspectionsFragment : BaseFragment<FragmentAppliedByMeBinding, AppliedByMeViewModel>(),
VerifiedSurpriseInspectionsNavigator{

    private lateinit var mAdapter: AppliedByMeAdapter

    /**
     * if true, then the data is for Applied For Me section.
     * if false, then the data is for Verified Surprise Inspections section.
     */
    private var isDataForAppliedByMe: Boolean = true

    override fun getLayoutId() = R.layout.fragment_applied_by_me
    override fun getViewModel() = AppliedByMeViewModel::class.java
    override fun getNavigator() = this@VerifiedSurpriseInspectionsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
//        viewAppliedListData =
//            arguments?.getParcelableArrayList<ViewAppliedListData>(Constants.SI_DATA)?.toList()

        isDataForAppliedByMe = arguments?.getBoolean(Constants.ADDED_BY_ME) ?: true

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        setUpRecyclerView(mBinding.rvListings)

        setUpObservers()
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

    }

    private fun setUpObservers() {
        //This observer setups viewPager with new adapter when new data is available
        mViewModel._viewAppliedLists.observe(this, Observer {
            val filteredData = mViewModel.filterData(it, isDataForAppliedByMe)
            mAdapter.submitList(filteredData)

            if (filteredData.isNullOrEmpty())
                mBinding.tvErrorText.visibility = View.VISIBLE
        })
    }
}
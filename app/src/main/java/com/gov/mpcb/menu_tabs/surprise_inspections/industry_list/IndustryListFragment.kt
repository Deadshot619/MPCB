package com.gov.mpcb.menu_tabs.surprise_inspections.industry_list

import android.os.Bundle
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIndustryListBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection.ApplyForSurpriseInspectionFragment
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.constants.Constants.Companion.VIEW_AVAILABLE_INDUSTRY_DATA
import com.gov.mpcb.utils.replaceFragment
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class IndustryListFragment : BaseFragment<FragmentIndustryListBinding, IndustryListViewModel>(),
    IndustryListNavigator {

    private lateinit var mAdapter: IndustryListAdapter

    override fun getLayoutId() = R.layout.fragment_industry_list
    override fun getViewModel() = IndustryListViewModel::class.java
    override fun getNavigator() = this@IndustryListFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.industry_list),
            showSearchBar = true,
            showCalendar = false,
            showBackButton = true
        )

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        setListeners()

        setUpRecyclerView(mBinding.rvIndustryList)

        setObservers()
    }

    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        mAdapter = IndustryListAdapter(IndustryListAdapter.OnClickListener {
            replaceFragment(
                fragment = ApplyForSurpriseInspectionFragment(),
                addToBackStack = true,
                bundle = Bundle().apply {
                    putParcelable(VIEW_AVAILABLE_INDUSTRY_DATA, it)
                }
            )
        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }

    /**
     * Method to setup Observers on this fragment
     */
    private fun setObservers() {
    }

    /**
     * Method to set all types of listeners for this fragment
     */
    private fun setListeners() {
        //Destroy the activity on click of back button
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.finish()
        }

        mBinding.toolbarLayout.searchBar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty())
                    mViewModel.getAvailableIndustryListsData(query)
                else
                    mViewModel.getAvailableIndustryListsData()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })

    }
}
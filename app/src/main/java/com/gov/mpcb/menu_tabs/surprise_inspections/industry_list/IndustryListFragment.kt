package com.gov.mpcb.menu_tabs.surprise_inspections.industry_list

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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
       //Setup Adapter
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
        paginationObservers()
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
                //When query is searched, call the api & reset the page no.
                if (!query.isNullOrEmpty()) {
                    mViewModel.getAvailableIndustryListsData(query)
                    mViewModel.resetCurrentPage()
                } else
                    mViewModel.getAvailableIndustryListsData()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false

        })

//        mBinding.toolbarLayout.searchBar.setOnCloseListener {
//            mViewModel.getAvailableIndustryListsData()
//            mViewModel.resetCurrentPage()
//            true
//        }

        paginationListeners()
    }

    /**
     * Method to set Observers on pagination
     */
    private fun paginationObservers() {
        //Observe totalPage
        mViewModel.totalPage.observe(viewLifecycleOwner, Observer {
            //If totalPages is greater than 1, then show the pagination layout, else hide it
            if (it > 1)
                mBinding.layoutPagination.clPagination.visibility = View.VISIBLE
            else
                mBinding.layoutPagination.clPagination.visibility = View.GONE

        })

        //Observe CurrentPage
        mViewModel.currentPage.observe(viewLifecycleOwner, Observer {
            //set pagination indicator
            mBinding.layoutPagination.paginationIndicator.text = "$it"

            //Do this only if there are pages available
            if (mViewModel.totalPage.value!! > 1) {
                when (it) {
                    //if currentPage & Total page are same, then hide 'Next' button & only show 'Previous' button
                    mViewModel.totalPage.value -> {
                        mBinding.layoutPagination.run {
                            paginationNext.visibility = View.INVISIBLE
                            paginationPrevious.visibility = View.VISIBLE
                        }
                    }
                    //if currentPage is 1, then hide 'Previous' button & only show 'Next' button
                    1 -> {
                        mBinding.layoutPagination.run{
                            paginationNext.visibility = View.VISIBLE
                            paginationPrevious.visibility = View.INVISIBLE
                        }
                    }
                    //if currentPage is between first & last, then show both 'Previous' & 'Next' button
                    else -> {
                        mBinding.layoutPagination.run {
                            paginationNext.visibility = View.VISIBLE
                            paginationPrevious.visibility = View.VISIBLE
                        }
                    }
                }
            }
        })
    }

    /**
     * Method to set Listeners on pagination
     */
    private fun paginationListeners(){
        //Next
        mBinding.layoutPagination.paginationNext.setOnClickListener {
            mViewModel.run {
                incrementCurrentPage()
                getAvailableIndustryListsData(
                    searchQuery = mBinding.toolbarLayout.searchBar.query.toString(),
                    pageNo = mViewModel.currentPage.value!!
                )
            }
        }

        //Previous
        mBinding.layoutPagination.paginationPrevious.setOnClickListener {
            mViewModel.run {
                decrementCurrentPage()
                getAvailableIndustryListsData(
                    searchQuery = mBinding.toolbarLayout.searchBar.query.toString(),
                    pageNo = mViewModel.currentPage.value!!
                )
            }
        }
    }
}
package com.gov.mpcb.menu_tabs.industry_directory.id_industry_list

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdIndustryListBinding
import com.gov.mpcb.menu_tabs.industry_directory.application_list.ApplicationListFragment
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.replaceFragment
import com.gov.mpcb.utils.showMessage

class IdIndustryListFragment :
    BaseFragment<FragmentIdIndustryListBinding, IdIndustryListViewModel>(),
    IdIndustryListNavigator {

    private lateinit var mAdapter: IdIndustryListAdapter

    override fun getLayoutId() = R.layout.fragment_id_industry_list
    override fun getViewModel() = IdIndustryListViewModel::class.java
    override fun getNavigator() = this@IdIndustryListFragment
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

        setUpListeners()

        setUpRecyclerView(mBinding.rvIndustryList)


        setObservers()
    }

    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = IdIndustryListAdapter(IdIndustryListAdapter.OnClickListener {
            //On click of a industry, open the 'Application List' Page
            //put the industry id in a bundle & pass it to fragment
            replaceFragment(
                fragment = ApplicationListFragment(),
                addToBackStack = true,
                bundle = Bundle().apply { putInt(Constants.INDUSTRY_ID, it.industryId) }
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
     * Method to setup all types of Listeners
     */
    private fun setUpListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.finish()
        }

        mBinding.toolbarLayout.searchBar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //When query is searched, call the api & reset the page no.
                if (!query.isNullOrEmpty()) {
                    mViewModel.getIndustryListsData(query)
                    mViewModel.resetCurrentPage()
                } else
                    mViewModel.getIndustryListsData()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Reset the searc
                if (newText == "") {
                    mViewModel.getIndustryListsData()
                    mViewModel.resetCurrentPage()
                }
                return true
            }

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
        mViewModel._totalPage.observe(viewLifecycleOwner, Observer {
            //If totalPages is greater than 1, then show the pagination layout, else hide it
            if (it > 1)
                mBinding.layoutPagination.clPagination.visibility = View.VISIBLE
            else
                mBinding.layoutPagination.clPagination.visibility = View.GONE

        })

        //Observe CurrentPage
        mViewModel._currentPage.observe(viewLifecycleOwner, Observer {
            //set pagination indicator
            mBinding.layoutPagination.paginationIndicator.text = "$it"

            //Do this only if there are pages available
            if (mViewModel._totalPage.value!! > 1) {
                when (it) {
                    //if currentPage & Total page are same, then hide 'Next' button & only show 'Previous' button
                    mViewModel._totalPage.value -> {
                        mBinding.layoutPagination.run {
                            paginationNext.visibility = View.INVISIBLE
                            paginationPrevious.visibility = View.VISIBLE
                        }
                    }
                    //if currentPage is 1, then hide 'Previous' button & only show 'Next' button
                    1 -> {
                        mBinding.layoutPagination.run {
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
    private fun paginationListeners() {
        //Next
        mBinding.layoutPagination.paginationNext.setOnClickListener {
            mViewModel.run {
                incrementCurrentPage()
                getIndustryListsData(
                    searchQuery = mBinding.toolbarLayout.searchBar.query.toString(),
                    pageNo = mViewModel._currentPage.value!!
                )
            }
        }

        //Previous
        mBinding.layoutPagination.paginationPrevious.setOnClickListener {
            mViewModel.run {
                decrementCurrentPage()
                getIndustryListsData(
                    searchQuery = mBinding.toolbarLayout.searchBar.query.toString(),
                    pageNo = mViewModel._currentPage.value!!
                )
            }
        }
    }
}

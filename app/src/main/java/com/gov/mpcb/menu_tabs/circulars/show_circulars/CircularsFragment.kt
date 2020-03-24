package com.gov.mpcb.menu_tabs.circulars.show_circulars

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentCircularsBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.ShowCircularsAdapter
import com.gov.mpcb.utils.CommonUtils
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CircularsFragment : BaseFragment<FragmentCircularsBinding, CircularsFragmentViewModel>(),
    CircularsFragmentNavigator {

    private lateinit var mAdapter: ShowCircularsAdapter

    override fun getLayoutId() = R.layout.fragment_circulars
    override fun getViewModel() = CircularsFragmentViewModel::class.java
    override fun getNavigator() = this@CircularsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.circulars),
            showSearchBar = true,
            showCalendar = false,
            showBackButton = true
        )

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        setListeners()

        setUpRecyclerView(mBinding.rvCircularsList)

        setObservers()
    }

    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = ShowCircularsAdapter(ShowCircularsAdapter.OnClickListener{
                CommonUtils.redirectUserToBrowser(activity!!, it.pdfLink)
        })

        recyclerView.run {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }
    }

    /**
     * Method to set all types of listeners for this fragment
     */
    private fun setListeners() {
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.finish()
        }

        paginationListeners()
    }

    /**
     * Method to setup Observers on this fragment
     */
    private fun setObservers() {
        paginationObservers()
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
                //TODO 24/03/2020 : Setup pagination with api
                /*getAvailableIndustryListsData(
                    searchQuery = mBinding.toolbarLayout.searchBar.query.toString(),
                    pageNo = mViewModel._currentPage.value!!
                )*/
            }
        }

        //Previous
        mBinding.layoutPagination.paginationPrevious.setOnClickListener {
            mViewModel.run {
                decrementCurrentPage()
                /*getAvailableIndustryListsData(
                    searchQuery = mBinding.toolbarLayout.searchBar.query.toString(),
                    pageNo = mViewModel._currentPage.value!!
                )*/
            }
        }
    }
}

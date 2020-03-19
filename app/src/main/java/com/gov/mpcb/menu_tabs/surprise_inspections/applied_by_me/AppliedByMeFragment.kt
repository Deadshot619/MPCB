package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeAdapter.OnClickListener
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class AppliedByMeFragment : BaseFragment<FragmentAppliedByMeBinding, AppliedByMeViewModel>(),
    AppliedByMeNavigator {

    private lateinit var mAdapter: AppliedByMeAdapter

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
//        viewAppliedListData =
//            arguments?.getParcelableArrayList<ViewAppliedListData>(Constants.SI_DATA)?.toList()

        isDataForAppliedByMe = arguments?.getBoolean(Constants.ADDED_BY_ME) ?: true

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        setUpRecyclerView(mBinding.rvListings)

        setUpObservers()

        setUpListeners()
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

    }


    private fun setUpListeners(){
        paginationListeners()
    }


    private fun setUpObservers() {
        //This observer setups viewPager with new adapter when new data is available
        mViewModel._viewAppliedLists.observe(this, Observer {
            val filteredData = mViewModel.filterData(it, isDataForAppliedByMe)
            mAdapter.submitList(filteredData)

            if (filteredData.isNullOrEmpty())
                mBinding.tvErrorText.visibility = View.VISIBLE
        })

        paginationObservers()
    }

    /**
     * Method to set Observers on pagination
     */
    private fun paginationObservers() {
        //Observe totalPage
        mViewModel.totalPage.observe(viewLifecycleOwner, Observer {
            //If totalPages is greater than 1, then show the pagination layout, else hide it
            if (it > 1)
                //TODO 19/03/2020 : Revert his after changes are made
                mBinding.layoutPagination.clPagination.visibility = /*View.VISIBLE*/ View.GONE
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
                getAppliedListsData(pageNo = mViewModel.currentPage.value!!)
            }
        }

        //Previous
        mBinding.layoutPagination.paginationPrevious.setOnClickListener {
            mViewModel.run {
                decrementCurrentPage()
                getAppliedListsData(pageNo = mViewModel.currentPage.value!!)
            }
        }
    }
}
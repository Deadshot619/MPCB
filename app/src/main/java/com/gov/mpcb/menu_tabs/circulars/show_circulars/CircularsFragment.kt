package com.gov.mpcb.menu_tabs.circulars.show_circulars

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentCircularsBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.ShowCircularsAdapter
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

    }

    /**
     * Method to setup RecyclerView
     */
    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        //Setup Adapter
        mAdapter = ShowCircularsAdapter(ShowCircularsAdapter.OnClickListener{
            showMessage(it.title)
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
    }
}

package com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection

import android.view.View
import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentApplyForSurpriseInspectionBinding
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class ApplyForSurpriseInspectionFragment : BaseFragment<FragmentApplyForSurpriseInspectionBinding, ApplyForSurpriseInspectionViewModel>(),
ApplyForSurpriseInspectionNavigator{

    private var LAYOUT_HIDDEN = true

    override fun getLayoutId() = R.layout.fragment_apply_for_surprise_inspection
    override fun getViewModel() = ApplyForSurpriseInspectionViewModel::class.java
    override fun getNavigator() = this@ApplyForSurpriseInspectionFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun onBinding() {
        //Set toolbar
        Constants.setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.apply_for_surprise_inspection),
            showSearchBar = false,
            showCalendar = false,
            showBackButton = true
        )

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        setListeners()

//        setUpRecyclerView(mBinding.rvIndustryList)

        setObservers()
    }

    private fun setListeners(){
        //Destroy the activity on click of back button
        mBinding.toolbarLayout.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        mBinding.layoutApplication.ivArrowDown.setOnClickListener {
            if (LAYOUT_HIDDEN) {
                mBinding.layoutApplication.lowerLayout.visibility = View.VISIBLE
                LAYOUT_HIDDEN = false
            } else{
                mBinding.layoutApplication.lowerLayout.visibility = View.GONE
                LAYOUT_HIDDEN = true
            }
        }
    }

    private fun setObservers(){}

}

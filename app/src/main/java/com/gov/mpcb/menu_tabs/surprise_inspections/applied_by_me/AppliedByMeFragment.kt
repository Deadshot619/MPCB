package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class AppliedByMeFragment : BaseFragment<FragmentAppliedByMeBinding, AppliedByMeViewModel>(),
AppliedByMeNavigator{

    override fun getLayoutId() = R.layout.fragment_applied_by_me
    override fun getViewModel() = AppliedByMeViewModel::class.java
    override fun getNavigator() = this@AppliedByMeFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        setUpListeners()
    }

    private fun setUpListeners() {

    }


}

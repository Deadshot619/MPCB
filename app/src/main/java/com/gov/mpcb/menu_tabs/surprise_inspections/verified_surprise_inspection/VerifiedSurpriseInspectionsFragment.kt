package com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection

import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentVerifiedSurpriseInspectionsBinding
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class VerifiedSurpriseInspectionsFragment : BaseFragment<FragmentVerifiedSurpriseInspectionsBinding, VerifiedSurpriseInspectionsViewModel>(),
    VerifiedSurpriseInspectionsNavigator {

    override fun getLayoutId() = R.layout.fragment_verified_surprise_inspections
    override fun getViewModel() = VerifiedSurpriseInspectionsViewModel::class.java
    override fun getNavigator() = this@VerifiedSurpriseInspectionsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        setUpListeners()
    }

    private fun setUpListeners() {

    }


}

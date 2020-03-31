package com.gov.mpcb.menu_tabs.industry_directory.consent

import androidx.fragment.app.Fragment
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentIdConsentBinding
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
class IdConsentFragment : BaseFragment<FragmentIdConsentBinding, IdConsentViewModel>(),
    IdConsentNavigator {

    override fun getLayoutId() = R.layout.fragment_id_consent
    override fun getViewModel() = IdConsentViewModel::class.java
    override fun getNavigator() = this@IdConsentFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.run {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
        }
    }


}

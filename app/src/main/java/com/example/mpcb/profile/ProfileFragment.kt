package com.example.mpcb.profile


import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentProfileBinding
import com.example.mpcb.utils.showMessage


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(), ProfileNavigator {
    override fun getLayoutId() = R.layout.fragment_profile
    override fun getViewModel() = ProfileViewModel::class.java
    override fun getNavigator() = this@ProfileFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, getString(R.string.profile_title))
    }


}

package com.example.mpcb.profile


import android.content.Context
import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentProfileBinding
import com.example.mpcb.utils.dialog.ChangePwdDialog
import com.example.mpcb.utils.showMessage


class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(), ProfileNavigator {

    private lateinit var changePwdDialog: ChangePwdDialog

    override fun getLayoutId() = R.layout.fragment_profile
    override fun getViewModel() = ProfileViewModel::class.java
    override fun getNavigator() = this@ProfileFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onNameError() = showMessage("Enter Name")
    override fun onEmailError() = showMessage("Enter Email")
    override fun onMobileError() = showMessage("Enter Mobile")
    override fun onCurrentPwdError() = showMessage("Enter Current Password")
    override fun onNewPwdError() = showMessage("Enter New Password")
    override fun onUpdateProfileSuccess(message: String) = showMessage(message)
    override fun onChangePwdSuccess(msg: String) {
        changePwdDialog.dismiss()
        showMessage(msg)
    }

    override fun onBinding() {
        mBinding.viewModel = mViewModel
        mBinding.model = mViewModel.getUserModel()
        setToolbar(mBinding.toolbarLayout, getString(R.string.profile_title))
        mBinding.toolbarLayout.imgCalendar.visibility = View.GONE

        mBinding.txtChangePwd.setOnClickListener {
            changePwdDialog = ChangePwdDialog(activity as Context, mViewModel)
            changePwdDialog.show()
        }
    }


}

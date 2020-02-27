package com.gov.mpcb.profile


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentProfileBinding
import com.gov.mpcb.login.LoginActivity
import com.gov.mpcb.utils.constants.Constants.Companion.setToolbar
import com.gov.mpcb.utils.dialog.ChangePwdDialog
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import com.gov.mpcb.utils.showMessage


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
    override fun onValidationError(message: String) = showMessage(message)


    override fun onUpdateProfileSuccess(message: String) = showMessage(message)
    override fun onChangePwdSuccess(msg: String) {
        changePwdDialog.dismiss()
        showMessage(msg)
    }

    override fun onBinding() {
        mBinding.viewModel = mViewModel
        mBinding.model = mViewModel.getUserModel()
        setToolbar(mBinding.toolbarLayout, getString(com.gov.mpcb.R.string.profile_title))
        mBinding.toolbarLayout.logout.visibility = View.VISIBLE
        mBinding.toolbarLayout.imgCalendar.visibility = View.GONE

        mBinding.txtChangePwd.setOnClickListener {
            changePwdDialog = ChangePwdDialog(activity as Context, mViewModel)
            changePwdDialog.show()
        }


        mBinding.toolbarLayout.logout.setOnClickListener {


            val builder by lazy {
                AlertDialog.Builder(context!!)
                    .setTitle(R.string.txt_logout_title)
                    .setMessage(R.string.txt_logout_message)
                    .setPositiveButton(R.string.action_ok) { _, _ -> logoutClicked() }
                    .setNegativeButton(R.string.action_cancel) { _, _ -> }
                    .create()
            }
            builder.show()


        }

    }

    private fun logoutClicked() {
        /*val sharedPreferences = activity?.getSharedPreferences( , Context.MODE_PRIVATE) ?: return
        sharedPreferences.edit().clear().apply()*/

        val preferencesHelper = PreferencesHelper.deletAll()


        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        onDestroy()
    }


}

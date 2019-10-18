package com.example.mpcb.reports.treatment


import android.view.View
import androidx.core.content.ContextCompat
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTreatmentBinding
import com.example.mpcb.reports.ReportsPageActivity
import com.example.mpcb.reports.ReportsPageNavigator
import com.example.mpcb.reports.ReportsPageViewModel
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.showMessage

class TreatmentFragment : BaseFragment<FragmentTreatmentBinding, ReportsPageViewModel>(),
    ReportsPageNavigator {
    override fun getLayoutId() = R.layout.fragment_treatment
    override fun getViewModel() = ReportsPageViewModel::class.java
    override fun getNavigator() = this@TreatmentFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        (getBaseActivity() as ReportsPageActivity).setToolbar(Constants.REPORT_3)

        setListeners()

        mBinding.btnSubmit.setOnClickListener { addReportFragment(Constants.REPORT_4) }

    }

    private fun setListeners() {
        mBinding.imgPrimaryExpandCollapse.setOnClickListener {
            if (mBinding.layLinPrimaryChild.visibility == View.VISIBLE) {
                mBinding.layLinPrimaryChild.visibility = View.GONE
                mBinding.imgPrimaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
            } else {
                mBinding.layLinPrimaryChild.visibility = View.VISIBLE
                mBinding.imgPrimaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
            }
        }

        mBinding.imgSecondaryExpandCollapse.setOnClickListener {
            if (mBinding.layLinSecondaryChild.visibility == View.VISIBLE) {
                mBinding.layLinSecondaryChild.visibility = View.GONE
                mBinding.imgSecondaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
            } else {
                mBinding.layLinSecondaryChild.visibility = View.VISIBLE
                mBinding.imgSecondaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
            }
        }

        mBinding.imgTertiaryExpandCollapse.setOnClickListener {
            if (mBinding.layLinTertiaryChild.visibility == View.VISIBLE) {
                mBinding.layLinTertiaryChild.visibility = View.GONE
                mBinding.imgTertiaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
            } else {
                mBinding.layLinTertiaryChild.visibility = View.VISIBLE
                mBinding.imgTertiaryExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
            }
        }

        mBinding.imgAdvanceExpandCollapse.setOnClickListener {
            if (mBinding.layLinAdvanceChild.visibility == View.VISIBLE) {
                mBinding.layLinAdvanceChild.visibility = View.GONE
                mBinding.imgAdvanceExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_down_arrow
                    )
                )
            } else {
                mBinding.layLinAdvanceChild.visibility = View.VISIBLE
                mBinding.imgAdvanceExpandCollapse.setImageDrawable(
                    ContextCompat.getDrawable(
                        getBaseActivity(),
                        R.drawable.ic_up_arrow
                    )
                )
            }
        }
    }
}

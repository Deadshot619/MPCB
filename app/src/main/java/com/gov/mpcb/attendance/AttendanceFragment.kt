package com.gov.mpcb.attendance


import android.view.View
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAttendanceBinding
import com.gov.mpcb.utils.showMessage


class AttendanceFragment : BaseFragment<FragmentAttendanceBinding, AttendanceViewModel>(), AttendanceNavigator {


    override fun getLayoutId() = R.layout.fragment_attendance
    override fun getViewModel() = AttendanceViewModel::class.java
    override fun getNavigator() = this@AttendanceFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, getString(R.string.attendance_title))
        mBinding.toolbarLayout.imgCalendar.visibility = View.GONE
    }


}

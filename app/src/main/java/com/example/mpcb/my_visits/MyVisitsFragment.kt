package com.example.mpcb.my_visits


import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentMyVisitsBinding
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.utils.addFragment
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.dialog.CheckInDialog
import com.example.mpcb.utils.dialog.DialogHelper
import com.example.mpcb.utils.locationservice.LocationHelper
import com.example.mpcb.utils.permission.PermissionUtils
import com.example.mpcb.utils.showMessage
import com.example.mpcb.visit_report.VisitReportFragment
import java.util.*


class MyVisitsFragment : BaseFragment<FragmentMyVisitsBinding, MyVisitsViewModel>(),
    MyVisitsNavigator {

    private lateinit var model: MyVisitModel
    private lateinit var dialogFragment: CheckInDialog

    override fun getLayoutId() = R.layout.fragment_my_visits
    override fun getViewModel() = MyVisitsViewModel::class.java
    override fun getNavigator() = this@MyVisitsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, getString(R.string.my_visits_title))
        setUpRecyclerView()
        mBinding.toolbarLayout.imgCalendar.setOnClickListener { showCalendarDialog() }

    }

    private fun showCalendarDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.e("Date", "" + year + " " + (month + 1) + " " + dayOfMonth)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }

    private fun setUpRecyclerView() {
        mBinding.rvMyVisits.layoutManager = LinearLayoutManager(getBaseActivity())
        val adapter = MyVisitsAdapter(getBaseActivity(), mViewModel)
        mBinding.rvMyVisits.adapter = adapter
        mViewModel.getVisitList().observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
        mViewModel.getVisitListData()
    }

    override fun onVisitItemClicked(item: MyVisitModel) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.VISIT_ITEM_KEY, item)
        addFragment(VisitReportFragment(), true, bundle)
    }

    override fun dismissCheckinDialog() {
        dialogFragment.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCheckInClicked(model: MyVisitModel) {

        this.model = model

        if (!LocationHelper.isLocationProviderEnabled(context!!)) {
            DialogHelper.showLocationAlertDialog(context!!)
        } else {
            if (activity!!.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    PermissionUtils.LOCATION_PERMISSTIONS,
                    100
                )
            } else {
                openCheckinDialog()
            }
        }
    }

    private fun openCheckinDialog() {
        mViewModel.getCurrentLocation()
        dialogFragment = CheckInDialog.newInstance(activity!!, model, mViewModel)
        dialogFragment.show(parentFragmentManager, MyVisitsFragment::class.java.simpleName)
    }

    override fun onCheckInSuccess(msg: String) {
        showMessage(msg)
        mViewModel.getVisitListData()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == 100) {
            openCheckinDialog()
        }
    }
}

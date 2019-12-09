package com.example.mpcb.my_visits


import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentMyVisitsBinding
import com.example.mpcb.network.response.CheckInfoModel
import com.example.mpcb.network.response.MyVisitModel
import com.example.mpcb.utils.addFragment
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.dialog.CheckInDialog
import com.example.mpcb.utils.dialog.DialogHelper
import com.example.mpcb.utils.dialog.MonthYearPickerDialog
import com.example.mpcb.utils.locationservice.LocationHelper
import com.example.mpcb.utils.permission.PermissionUtils
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.getBooleanPreference
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper.setBooleanPreference
import com.example.mpcb.utils.showMessage
import com.example.mpcb.visit_report.VisitReportFragment
import java.util.*


class MyVisitsFragment : BaseFragment<FragmentMyVisitsBinding, MyVisitsViewModel>(),
    MyVisitsNavigator, DatePickerDialog.OnDateSetListener {


    private lateinit var adapter: MyVisitsAdapter

    //These variables will be used to set adapter's date to start & end of month
    private lateinit var fromDate: String
    private lateinit var toDate: String

    override fun showAlert(message: String) {
        showMessage("To be implemented")
        //TODO 26/11/19 To be implemented
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        fromDate = "$year-$month-${dayOfMonth + 1}"
        toDate = "$year-$month-${dayOfMonth + 31}"

        mViewModel.getVisitListData(
            fromDate = fromDate,
            toDate = toDate
        )

        // To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var models: CheckInfoModel
    private lateinit var model: MyVisitModel
    private lateinit var dialogFragment: CheckInDialog

    override fun getLayoutId() = R.layout.fragment_my_visits
    override fun getViewModel() = MyVisitsViewModel::class.java
    override fun getNavigator() = this@MyVisitsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, getString(R.string.my_visits_title), showSearchBar = true)

        setUpRecyclerView()
        setupSearchListener()
        mBinding.toolbarLayout.imgCalendar.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(this)

            pd.show(fragmentManager!!, "MonthYearPickerDialog")
        }


    }

    override fun onStart() {
        super.onStart()
        showMessage("${getBooleanPreference(Constants.VISIT_STATUS)}")
        //If FORM_COMPLETE_STATUS is true, then refresh the page to show visit status as completed.
        if (getBooleanPreference(Constants.FORM_COMPLETE_STATUS))
            mViewModel.getVisitListData(
                fromDate = fromDate,
                toDate = toDate
            )
    }

    override fun onResume() {
        super.onResume()

    }

    /**
     * This method is used to setup a search listener for searchBar
     */
    private fun setupSearchListener(){
        mBinding.toolbarLayout.searchBar.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                   return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    //Call the adapter's filter function
                    adapter.filter.filter(newText)
                   return false
                }

            }
        )
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
        adapter = MyVisitsAdapter(getBaseActivity(), mViewModel)
        mBinding.rvMyVisits.adapter = adapter
        mViewModel.getVisitList().observe(viewLifecycleOwner, Observer {
            if (it.status == "1" && it.data.size > 0)
                adapter.updateList(it.data)
            else if (it.status == "1" && it.data.size == 0){
                //Show Empty list
                adapter.updateList(it.data)
                showMessage(it.message)
            }else
                showMessage(it.message)

            //Set Form Complete Status to false
            setBooleanPreference(Constants.FORM_COMPLETE_STATUS, false)
        })
        val calendar = Calendar.getInstance()
        fromDate =
            calendar.get(Calendar.YEAR).toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        toDate =
            calendar.get(Calendar.YEAR).toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        mViewModel.getVisitListData(fromDate, toDate)
    }

    override fun onVisitItemClicked(viewModel: MyVisitModel) {
        showMessage(viewModel.industryIMISId)
        val bundle = Bundle()
        bundle.putParcelable(Constants.VISIT_ITEM_KEY, viewModel)
        addFragment(VisitReportFragment(), true, bundle)
    }

    override fun onAlreadyCheckedIn(viewModel: CheckInfoModel) {
        this.models = viewModel

        //openCheckinDialog()


       // openCheckInfoDialog()

       /* mViewModel.getCurrentLocation()
        dialogFragment = CheckInDialog.newInstance(activity!!, model, mViewModel)
        dialogFragment.show(parentFragmentManager, MyVisitsFragment::class.java.simpleName)*/

    }

    override fun dismissCheckinDialog() {
        dialogFragment.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCheckInClicked(model: MyVisitModel) {
        this.model = model

        if (model.checkInStatus == 1){
            openCheckinDialog()
        }else{
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
    }

    private fun openCheckinDialog() {
        mViewModel.getCurrentLocation()
        dialogFragment = CheckInDialog.newInstance(activity!!, model, mViewModel)
        dialogFragment.show(parentFragmentManager, MyVisitsFragment::class.java.simpleName)
    }


    private fun openCheckInfoDialog() {

        dialogFragment = CheckInDialog.newInstance(activity!!, model, mViewModel)
        dialogFragment.show(parentFragmentManager, MyVisitsFragment::class.java.simpleName)
    }



    override fun onCheckInSuccess(msg: String) {
        showMessage(msg)
        mViewModel.getVisitListData(fromDate = fromDate, toDate = toDate)
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

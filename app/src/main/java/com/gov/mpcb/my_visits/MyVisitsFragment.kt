package com.gov.mpcb.my_visits


import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentMyVisitsBinding
import com.gov.mpcb.my_visits.MyVisitsUtils.Companion.myVisitsSpinnerSelectedUser
import com.gov.mpcb.my_visits.MyVisitsUtils.Companion.myVisitsSpinnerSelectedUserId
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.network.response.CheckInfoModel
import com.gov.mpcb.network.response.LoginResponse
import com.gov.mpcb.network.response.MyVisitModel
import com.gov.mpcb.network.response.Users
import com.gov.mpcb.utils.addFragment
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.dialog.CheckInDialog
import com.gov.mpcb.utils.dialog.DialogHelper
import com.gov.mpcb.utils.dialog.MonthYearPickerDialog
import com.gov.mpcb.utils.locationservice.LocationHelper
import com.gov.mpcb.utils.permission.PermissionUtils
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper.getBooleanPreference
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper.setBooleanPreference
import com.gov.mpcb.utils.showMessage
import com.gov.mpcb.visit_report.VisitReportFragment
import java.util.*

class MyVisitsFragment : BaseFragment<FragmentMyVisitsBinding, MyVisitsViewModel>(),
    MyVisitsNavigator, DatePickerDialog.OnDateSetListener {


    private lateinit var adapter: MyVisitsAdapter

    //These variables will be used to set adapter's date to start & end of month
    private lateinit var fromDate: String
    private lateinit var toDate: String

    /**
     * These variables will be used to get user Data from Shared Pref
     */
    private val userModel by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    override fun showAlert(message: String) {
        showMessage(message = message)
        //TODO 26/11/19 To be implemented
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        fromDate = "$year-$month-${dayOfMonth + 1}"
        toDate = "$year-$month-${dayOfMonth + 31}"

        //Set Year & Month values in MonthYearPickerDialog
        MonthYearPickerDialog.run {
            yearMyVisit = year
            monthMyVisit = month
        }

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
        //Set calendarConstant to MyVisit
        MonthYearPickerDialog.calendarConstant = Constants.Companion.CalendarConstant.MY_VISIT

        //Setup Toolbar
        setToolbar(mBinding.toolbarLayout, getString(R.string.my_visits_title), showSearchBar = true)

        setUpRecyclerView()
        setupSearchListener()
        mBinding.toolbarLayout.imgCalendar.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(this)

            pd.show(fragmentManager!!, "MonthYearPickerDialog")
        }

        //Check if the user is a SubOrdinate User
        //If the user is subordinate user Show the dropdown & get the UserList from Api
        checkIfSubordinateUser()
    }

    /**
     * Method to check if the user is Sub-Ordinate user
     * If he is, then Make spinner visible & populate data accordingly
     * Also set listener to Spinner.
     */
    private fun checkIfSubordinateUser() {
        if (userModel.hasSubbordinateOfficers == 1) {
            mBinding.spinnerUserList.visibility = View.VISIBLE

//            mViewModel.userSpinnerData.observe(viewLifecycleOwner, Observer {
//                it?.let {
//                    setSpinnerData(it)
//                }
//            })

            //get User List Data
            mViewModel.getUserListData()

        }
    }

    //Set data to spinner
    override fun setSpinnerData(users: List<Users>) {
        //create spinnerArray that will hold data from Api
        val spinnerArray = ArrayList<String>()
        for (element in users)
            spinnerArray.add(element.userName)

        //Create a adapter that will be used to set in Spinner
        val adapter = ArrayAdapter<String>(
            context!!, android.R.layout.simple_spinner_item, spinnerArray
        )

        //Set User id of first user
        if (myVisitsSpinnerSelectedUserId < 0)
            myVisitsSpinnerSelectedUserId = users[0].userId

        //Set dropdown View Resource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Set adapter to spinner
        mBinding.spinnerUserList.adapter = adapter

        try {
            mBinding.spinnerUserList.run {
                //Set data to spinner
                if (myVisitsSpinnerSelectedUser != selectedItem) {
                    setSelection(
                        spinnerArray.indexOf(myVisitsSpinnerSelectedUser)
                    )
//                    dashboardSpinnerSelectedUser = selectedItem.toString()
                }
            }
        } catch (e: Exception) {
            showMessage(e.message.toString())
        }

        mBinding.spinnerUserList.let {
            it.selectedItem?.run {
                myVisitsSpinnerSelectedUser = this.toString()
                //                    dashboardSpinnerSelectedUserId =
                //                        mViewModel.userSpinnerData.value?.get().userId!!
            }

            //Set listener to Spinner
            it.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {

                        if (myVisitsSpinnerSelectedUser != it.getItemAtPosition(p2).toString()                            ) {

                            //Set selected User
                            myVisitsSpinnerSelectedUser =
                                it.getItemAtPosition(p2).toString()

                            //Set selected User id
                            myVisitsSpinnerSelectedUserId =
                                users[p2].userId

//                            showMessage(myVisitsSpinnerSelectedUser)

                            //Get dashboard data for current user
                            mViewModel.getVisitListData(fromDate, toDate)
                        }
                    }
                }
        }

    }

    override fun onStart() {
        super.onStart()
        //If FORM_COMPLETE_STATUS is true, then refresh the page to show visit status as completed.
        if (getBooleanPreference(Constants.FORM_COMPLETE_STATUS))
            mViewModel.getVisitListData(
                fromDate = fromDate,
                toDate = toDate
            )
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

        //Check if Year & Month is set in DatePickerDialog
        fromDate = if (MonthYearPickerDialog.yearMyVisit >= 0 && MonthYearPickerDialog.monthMyVisit >= 0)
            MonthYearPickerDialog.yearMyVisit.toString() + "-" + (MonthYearPickerDialog.monthMyVisit ).toString() + "-" + calendar.getActualMinimum(
                Calendar.DAY_OF_MONTH
            ).toString()
        else
            calendar.get(Calendar.YEAR).toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH)

        //Check if Year & Month is set in DatePickerDialog
        toDate =
            if (MonthYearPickerDialog.yearMyVisit >= 0 && MonthYearPickerDialog.monthMyVisit >= 0)
                MonthYearPickerDialog.yearMyVisit.toString() + "-" + (MonthYearPickerDialog.monthMyVisit ).toString() + "-" +
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString()
            else
                calendar.get(Calendar.YEAR).toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        if (userModel.hasSubbordinateOfficers != 1){
            mViewModel.getVisitListData(fromDate, toDate)
        }

    }

    override fun onVisitItemClicked(viewModel: MyVisitModel) {
//        showMessage(viewModel.industryIMISId)
        val bundle = Bundle()
        bundle.putParcelable(Constants.VISIT_ITEM_KEY, viewModel)

        /*
         * This block of code initializes a report so that some forms don't
         * show empty screen.
         */
        if (viewModel.visitStatus != "Visited"){
            val reportRequest =
                //If the report does not exist, initialize a new one
                if(getReportData(viewModel.industryIMISId) == null)
                    ReportRequest()
                else    //If the report exists, get that report
                    getReportData(viewModel.industryIMISId)

            //Reports are saved according to their Report No.
            PreferencesHelper.setPreferences(
                key = viewModel.industryIMISId,
                value = Gson().toJson(reportRequest)
            )
        }

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

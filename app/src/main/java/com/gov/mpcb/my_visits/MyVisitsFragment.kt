package com.gov.mpcb.my_visits


import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
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
import com.gov.mpcb.utils.constants.Constants.Companion.setToolbar
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
import kotlin.collections.ArrayList


class MyVisitsFragment : BaseFragmentReport<FragmentMyVisitsBinding, MyVisitsViewModel>(),
    MyVisitsNavigator, DatePickerDialog.OnDateSetListener {


    private lateinit var adapter: MyVisitsAdapter

    //These variables will be used to set adapter's date to start & end of month
    private lateinit var fromDate: String
    private lateinit var toDate: String
    private lateinit var previousMonth: String  //stores date in MM/YYYY
    private lateinit var previousMonthName: String  //stores month name

    //Variable to indicate whether the current list is Uncompleted visit list or Normal visit list
    private var isUncompletedVisitList: Boolean = false

    /**
     * These variables will be used to get user Data from Shared Pref
     */
    private val userModel by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calender = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        fromDate = "$year-$month-${dayOfMonth + 1}"
        toDate = "$year-$month-${calender.getActualMaximum(Calendar.DAY_OF_MONTH)}"

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
    override fun showAlert(message: String) {
        showMessage(message = message)
    }

    override fun checkSubordinateUsers() {
        //Check if the user is a SubOrdinate User
        //If the user is subordinate user Show the dropdown & get the UserList from Api
        checkIfSubordinateUser()
    }

    override fun callUncompletedVisitData() {
        mViewModel.getUncompletedVisitData(date = previousMonth)      //retrieve uncompleted visit list data
    }

    override fun openUnvisitReviewDialog(data: MyVisitModel) {
        openReviewDialog(data)  //open review dialog
    }

    override fun onBinding() {
        //Set calendarConstant to MyVisit
        MonthYearPickerDialog.calendarConstant = Constants.Companion.CalendarConstant.MY_VISIT

        //Setup Toolbar
        setToolbarStyle(true)

        //set date variables
        setDate(Calendar.getInstance())

        mViewModel.getUncompletedVisitData(date = previousMonth)

        setUpObservers()

        setupSearchListener()

        mBinding.toolbarLayout.imgCalendar.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(this)

            pd.show(fragmentManager!!, "MonthYearPickerDialog")
        }

    }

    /**
     * Method to set dates to [fromDate] & [toDate] variables
     *
     * @param calendar Takes a Calendar instance as argument
     */
    private fun setDate(calendar: Calendar) {
        //Check if Year & Month is set in DatePickerDialog
        fromDate =
            if (MonthYearPickerDialog.yearMyVisit >= 0 && MonthYearPickerDialog.monthMyVisit >= 0)
                MonthYearPickerDialog.yearMyVisit.toString() + "-" + (MonthYearPickerDialog.monthMyVisit).toString() + "-" + calendar.getActualMinimum(
                    Calendar.DAY_OF_MONTH
                ).toString()
            else
                calendar.get(Calendar.YEAR)
                    .toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                        calendar.getActualMinimum(Calendar.DAY_OF_MONTH)

        //Check if Year & Month is set in DatePickerDialog
        toDate =
            if (MonthYearPickerDialog.yearMyVisit >= 0 && MonthYearPickerDialog.monthMyVisit >= 0)
                MonthYearPickerDialog.yearMyVisit.toString() + "-" + (MonthYearPickerDialog.monthMyVisit).toString() + "-" +
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString()
            else
                calendar.get(Calendar.YEAR)
                    .toString() + "-" + (calendar.get(Calendar.MONTH) + 1).toString() + "-" +
                        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        //Set calendar to previous Month
        calendar.add(Calendar.MONTH, -1)
        //set date in MM/YYYY format
        previousMonth = "${calendar[Calendar.MONTH] + 1}-${calendar[Calendar.YEAR]}"
        //set full month name
        previousMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US) ?: ""
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
            //visit list data will be called after loading the spinner
            mViewModel.getUserListData()

        } else {            //If the user isn't a subordinate, get VisitListData
            //get visit list data
            mViewModel.getVisitListData(fromDate, toDate)
        }
    }

    //Set data to spinner
    override fun setSpinnerData(users: List<Users>) {
        //create spinnerArray that will hold data from Api
        val spinnerArray = ArrayList<String>()

        /*
         *  Using users.dropLast(1) to remove last element since the last element is always
         * 'ALL' which we don't want here.
         */
        for (element in users.dropLast(1)) {
//            if (!element.userName.contains("ALL")) {
//                spinnerArray.add(element.userName)
//            }
            spinnerArray.add(element.userName)

        }
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

                        if (myVisitsSpinnerSelectedUser != it.getItemAtPosition(p2).toString()) {

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
        if (getBooleanPreference(Constants.FORM_COMPLETE_STATUS)) {
            //If current list was Uncompleted Visit List, then load uncompleted visit data first
            if (isUncompletedVisitList)
                mViewModel.getUncompletedVisitData(previousMonth)
            else    //else load normal visit data
                mViewModel.getVisitListData(fromDate = fromDate, toDate = toDate)

            //Set Form Complete Status to false
            setBooleanPreference(Constants.FORM_COMPLETE_STATUS, false)
        }
    }

    /**
     * This method is used to setup a search listener for searchBar
     */
    private fun setupSearchListener() {
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

    private fun setUpRecyclerView(isUncompletedVisitPresent: Int) {
        adapter = MyVisitsAdapter(getBaseActivity(), mViewModel, isUncompletedVisitPresent)

        mBinding.rvMyVisits.layoutManager = LinearLayoutManager(getBaseActivity())
        mBinding.rvMyVisits.adapter = adapter

    }

    /**
     * Method to setup observers
     */
    private fun setUpObservers() {
        //Observer to setup recycler adapter
        mViewModel.getVisitList().observe(viewLifecycleOwner, Observer {
            if (it.status == "1" && it.data.size > 0)
                adapter.updateList(it.data)
            else if (it.status == "1" && it.data.size == 0) {
                //Show Empty list
                adapter.updateList(it.data)
                showMessage(it.message)
            } else
                showMessage(it.message)

        })

        mViewModel.uncompletedVisitList.observe(viewLifecycleOwner, Observer {
            setUpRecyclerView(isUncompletedVisitPresent = it.isUncompletedVisitPresent)
            adapter.updateList(it.data as ArrayList<MyVisitModel>)

            if (it.data.isNullOrEmpty()) {
                setToolbarStyle(true)

                isUncompletedVisitList = false
            } else {
                setToolbarStyle(
                    false,
                    "Uncompleted visit list for $previousMonthName (${it.data.size})"
                )
            }

            isUncompletedVisitList = true

        })
    }

    /**
     * As the toolbar in this fragment is dynamic(changes according to the list), this method will be used to switch between toolbar
     * styles
     */
    private fun setToolbarStyle(normal: Boolean, customToolbarText: String = "") {
        if (normal) {    //if true, toolbar will be set back to its original state
            //Setup Toolbar
            setToolbar(
                mBinding.toolbarLayout,
                getString(R.string.my_visits_title),
                showSearchBar = true,
                showCalendar = true
            )

            mBinding.toolbarLayout.run {
                //set toolbar alpha to 0, to make it transparent
                toolbar.background?.alpha = 0
                //change color of title back to its original color
                txtToolbarTitle.setTextColor(resources.getColor(R.color.toolbar_text))
            }
        } else {  //f false, change toolbar style to represent Uncompleted visited list
            //Setup Toolbar
            setToolbar(
                mBinding.toolbarLayout,
                customToolbarText,
                showSearchBar = false,
                showCalendar = false
            )

            mBinding.toolbarLayout.run {
                //change bg color of background to red & its alpha to 255(not transparent)
                toolbar.setBackgroundResource(R.color.red)
                toolbar.background?.alpha = 255
                //set text color to white so it will be visible
                txtToolbarTitle.setTextColor(resources.getColor(R.color.white))
            }
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
        if (viewModel.visitStatus != "Visited") {
            val reportRequest =
                //If the report does not exist, initialize a new one
                if (getReportData(viewModel.industryIMISId) == null)
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
    //openCheckinDialog()


    // openCheckInfoDialog()

    /* mViewModel.getCurrentLocation()
     dialogFragment = CheckInDialog.newInstance(activity!!, model, mViewModel)
     dialogFragment.show(parentFragmentManager, MyVisitsFragment::class.java.simpleName)*/

    override fun onAlreadyCheckedIn(model: CheckInfoModel) {
        this.models = model

        //openCheckinDialog()


        // openCheckInfoDialog()

        /* mViewModel.getCurrentLocation()
         dialogFragment = CheckInDialog.newInstance(activity!!, model, mViewModel)
         dialogFragment.show(parentFragmentManager, MyVisitsFragment::class.java.simpleName)*/

    }

    //Method to dismiss Checkin dialog
    override fun dismissCheckinDialog() {
        dialogFragment.dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCheckInClicked(model: MyVisitModel) {
        this.model = model

        if (model.checkInStatus == 1) {
            openCheckinDialog(isUncompletedVisitList)
        } else {
            if (!LocationHelper.isLocationProviderEnabled(context!!)) {
                DialogHelper.showLocationAlertDialog(context!!)
            } else {
                if (activity!!.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        PermissionUtils.LOCATION_PERMISSTIONS,
                        100
                    )
                } else {
                    openCheckinDialog(isUncompletedVisitList)
                }
            }
        }
    }

    /**
     * Method to open CheckIn Dialog
     */
    private fun openCheckinDialog(isUncompletedVisitList: Boolean = false) {
        mViewModel.getCurrentLocation()
        dialogFragment =
            CheckInDialog.newInstance(activity!!, model, mViewModel)
        dialogFragment.show(
            parentFragmentManager,
            MyVisitsFragment::class.java.simpleName
        )
    }

    /**
     * Method to open dialog to enter review when uncompleted visits are present
     */
    private fun openReviewDialog(data: MyVisitModel) {
        //inflate view
        val viewInflated = LayoutInflater.from(context)
            .inflate(R.layout.layout_unvisit_remark, view as ViewGroup, false)

        //set Industry Name
        viewInflated.findViewById<TextView>(R.id.tv_industry_name).text = data.industryName
        //set Industry ID
        viewInflated.findViewById<TextView>(R.id.tv_industry_id).text = data.industryIMISId

        //get view for review edit text
        val edtReview = viewInflated.findViewById<TextInputEditText>(R.id.edt_review_field)

        //Show dialog
        val dialog = AlertDialog.Builder(context!!).apply {
            setView(viewInflated)
            setPositiveButton("Submit", null)
        }.show()

        //This is done so that alert dialog do not dismiss when clicked on submit button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (edtReview.text.let { it?.trim() }.isNullOrEmpty()) {
                edtReview.error = "Please enter a reason"
            } else { //If the text is present then submit the remark.
                mViewModel.submitRemark(
                    visitId = data.visitSchedulerId.toString(),
                    remarks = edtReview.text.toString()
                )
                dialog.dismiss()
            }
        }
    }

    /*
     * This method is executed when check-in info is submitted successfully.
     * This reloads the data of My Visit list
     */
    override fun onCheckInSuccess(msg: String) {
        showMessage(msg)

        //If current list was Uncompleted Visit List, then load uncompleted visit data first
        if (isUncompletedVisitList)
            mViewModel.getUncompletedVisitData(date = previousMonth)
        else    //else load normal visit data
            mViewModel.getVisitListData(fromDate = fromDate, toDate = toDate)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openCheckinDialog(isUncompletedVisitList)
                } else { // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    // user rejected the permission
                    val showRationale =
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    if (!showRationale) {
                        // user also CHECKED "never ask again"
                        // you can either enable some fall back,
                        // disable features of your app
                        // or open another dialog explaining
                        // again the permission and directing to
                        // the app setting
                        Snackbar.make(
                            mBinding.root,
                            "Grant Location permission to continue!",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Open") {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context?.packageName, null);
                                intent.data = uri
                                startActivity(intent)
                            }
                            .show()

                    } else {
                        showMessage("You need to accept this permission to continue")
                    }
                }
                return
            }
        }
    }

    override fun setDataToViews() {
        TODO("not implemented")
    }
}

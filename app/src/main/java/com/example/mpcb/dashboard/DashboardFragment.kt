package com.example.mpcb.dashboard


import android.app.DatePickerDialog
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.dashboard.DashboardUtils.Companion.dashboardSpinnerSelectedUser
import com.example.mpcb.dashboard.DashboardUtils.Companion.dashboardSpinnerSelectedUserId
import com.example.mpcb.databinding.FragmentDashboardBinding
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.Users
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.createChannel
import com.example.mpcb.utils.dialog.MonthYearPickerDialog
import com.example.mpcb.utils.dialog.MonthYearPickerDialog.Companion.monthDashboard
import com.example.mpcb.utils.dialog.MonthYearPickerDialog.Companion.yearDashboard
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*
import kotlin.collections.ArrayList

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    DashboardNavigator, DatePickerDialog.OnDateSetListener {

    /**
     * These variables will be used to get user Data from Shared Pref
     */
    private val userModel by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
    }

    private lateinit var fromDate: String

    //Dashboard Dates
    private lateinit var _currentYear: Number
    private lateinit var _currentMonth: Number
    private lateinit var _year_1: Number
    private lateinit var _month_1: Number
    private lateinit var _year_2: Number
    private lateinit var _month_2: Number
    private lateinit var _year_3: Number
    private lateinit var _month_3: Number

    /**
     * Method to set 'Month' & 'Year' to Date variables.
     */
    private fun setDataToDateIconVariables() {
        val calendar: Calendar = getInstance()

        _currentYear = calendar.get(YEAR)
        _currentMonth = calendar.get(MONTH) + 1

        calendar.add(MONTH, -1)

        _year_1 = calendar.get(YEAR)
        _month_1 = calendar.get(MONTH) + 1

        calendar.add(MONTH, -1)

        _year_2 = calendar.get(YEAR)
        _month_2 = calendar.get(MONTH) + 1

        calendar.add(MONTH, -1)

        _year_3 = calendar.get(YEAR)
        _month_3 = calendar.get(MONTH) + 1
    }


    override fun dashBoardTest(DashboardDataResponse: DashboardDataResponse) {
        // onBinding()
        //  Toast.makeText(activity!!, "Fragment!!!", Toast.LENGTH_LONG).show()
        mBinding.invalidateAll()
    }

    override fun showAlert(errorMessage: String) {
        Toast.makeText(activity!!, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calender = getInstance()
        val day = calender.getActualMinimum(DAY_OF_MONTH)

        //Set Year & Month values in MonthYearPickerDialog
        MonthYearPickerDialog.run {
            yearDashboard = year
            monthDashboard = month
        }

        //Select icon on Dashboard
        selectDashboardDateIcon(year = year, month = month)

        fromDate = "$year-$month-$day"
        mViewModel.getDashboardData(fromDate)
    }

    override fun getLayoutId() = R.layout.fragment_dashboard
    override fun getViewModel() = DashboardViewModel::class.java
    override fun getNavigator() = this@DashboardFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set calendarConstant to DASHBOARD
        MonthYearPickerDialog.calendarConstant = Constants.Companion.CalendarConstant.DASHBOARD

        //Set lifeCycle owner to this fragment
        mBinding.lifecycleOwner = this

        mBinding.dashboardModel = mViewModel.getDashboardModel()

        setToolbar(mBinding.toolbarLayout, "MPCB")

        //Toolbar
        mBinding.toolbarLayout.imgCalendar.visibility = View.GONE

        //Sets data in date icons
        setDataToDateIconVariables()

        setDateView()

        //Check if the user is a SubOrdinate User
        //If the user is subordinate user Show the dropdown & get the UserList from Api
        checkIfSubordinateUser()

        //sets initial data on Dashboard
        setInitialDashboardData()

        setListeners()

        setDateView()

        //Create Notification channel
        createChannel(
            context = context!!,
            channelId = getString(R.string.channel_id_1),
            channelName = getString(R.string.channel_name_1)
        )
    }

    /**
     * Method to set Initial Data on Dashboard.
     */
    private fun setInitialDashboardData() {
        val calendar = getInstance()

        //Check if Year & Month is set in DatePickerDialog
        if (yearDashboard >= 0 && monthDashboard >= 0) {
            fromDate =
                yearDashboard.toString() + "-" + (monthDashboard).toString() + "-" + calendar.getActualMinimum(
                    DAY_OF_MONTH
                ).toString()

            //Select Date icon
            selectDashboardDateIcon(yearDashboard, monthDashboard)
        } else {
            fromDate =
                calendar.get(YEAR).toString() + "-" + (calendar.get(MONTH) + 1).toString() + "-" + calendar.getActualMinimum(
                    DAY_OF_MONTH
                ).toString()

            //Select Date icon
            selectDashboardDateIcon(calendar.get(YEAR), (calendar.get(MONTH) + 1))
        }

        /*
         * If there are sub-Ordinate Users, then do not get the initial Dashboard Data through
         * this method.
         */
        if (userModel.hasSubbordinateOfficers != 1) {
            mViewModel.getDashboardData(fromDate)
        }
    }

    /**
     * Method to check if the user is Sub-Ordinate user
     * If he is, then Make spinner visible & populate data accordingly
     * Also set listener to Spinner.
     */
    private fun checkIfSubordinateUser() {
        if (userModel.hasSubbordinateOfficers == 1) {
            //MAke Spinner visible
            mBinding.toolbarLayout.spinnerUserList.visibility = View.VISIBLE

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
            context, android.R.layout.simple_spinner_item, spinnerArray
        )

        //Set User id of first user
        if (dashboardSpinnerSelectedUserId < 0)
            dashboardSpinnerSelectedUserId = users[0].userId

        //Set dropdown View Resource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Set adapter to spinner
        mBinding.toolbarLayout.spinnerUserList.adapter = adapter

        try {
            mBinding.toolbarLayout.spinnerUserList.run {
                //Set data to spinner
                if (dashboardSpinnerSelectedUser != selectedItem) {
                    setSelection(
                        spinnerArray.indexOf(dashboardSpinnerSelectedUser)
                    )
//                    dashboardSpinnerSelectedUser = selectedItem.toString()
                }
            }
        } catch (e: Exception) {
            showMessage(e.message.toString())
        }

        mBinding.toolbarLayout.spinnerUserList.let {
            //Set th selected item to
            it.selectedItem?.run {
                dashboardSpinnerSelectedUser = this.toString()
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

                        if (dashboardSpinnerSelectedUser != it.getItemAtPosition(p2).toString()) {
                            //Set seleted User
                            dashboardSpinnerSelectedUser = it.getItemAtPosition(p2).toString()
                            //Set selected User id
                            dashboardSpinnerSelectedUserId =
                                users[p2].userId

//                            showMessage(dashboardSpinnerSelectedUser)

                            //Get dashboard data for current user
                            mViewModel.getDashboardData(fromDate)
                        }
                    }
                }
        }


    }

    /**
     * Method to Initialize Date icons
     */
    private fun setDateView() {

        val calendar = getInstance()
        val currentTime = calendar.time
        val monthFormat = SimpleDateFormat("MMM")

        with(mBinding) {
            tvMonthOne.text =
                monthFormat.format(currentTime)
            tvYearOne.text =
                calendar.get(YEAR).toString()

            calendar.add(MONTH, -1)
            tvMonthTwo.text =
                monthFormat.format(calendar.time)
            tvYearTwo.text =
                calendar.get(YEAR).toString()

            calendar.time = currentTime
            calendar.add(MONTH, -2)
            tvMonthThree.text =
                monthFormat.format(calendar.time)
            tvYearThree.text =
                calendar.get(YEAR).toString()

            calendar.time = currentTime
            calendar.add(MONTH, -3)
            tvMonthFour.text =
                monthFormat.format(calendar.time)
            tvYearFour.text =
                calendar.get(YEAR).toString()

        }
    }

    /**
     * Set listeners to views
     */
    private fun setListeners() {
        //Date icon 1
        mBinding.monthLayOne.setOnClickListener {
            onDateSet(null, _currentYear.toInt(), _currentMonth.toInt(), 0)
//            selectDashboardDate(1)
        }

        //Date icon 2
        mBinding.monthLayTwo.setOnClickListener {
            onDateSet(null, _year_1.toInt(), _month_1.toInt(), 0)
//            selectDashboardDate(2)
        }

        //Date icon 3
        mBinding.monthLayThree.setOnClickListener {
            onDateSet(null, _year_2.toInt(), _month_2.toInt(), 0)
//            selectDashboardDate(3)
        }

        //Date icon 4
        mBinding.monthLayFour.setOnClickListener {
            onDateSet(null, _year_3.toInt(), _month_3.toInt(), 0)
//            selectDashboardDate(4)
        }

        mBinding.calPickerLay.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(this)
            pd.show(fragmentManager!!, "MonthYearPickerDialog")
        }
    }

    /**
     * This method is used to select Date Views (icons) in the Dashboard
     */
    private fun selectDashboardDateIcon(year: Int, month: Int) {
        when {
            year == _currentYear && month == _currentMonth -> selectDateView(1)
            year == _year_1 && month == _month_1 -> selectDateView(2)
            year == _year_2 && month == _month_2 -> selectDateView(3)
            year == _year_3 && month == _month_3 -> selectDateView(4)
            else -> selectDateView(0)
        }
    }

    /**
     * Method to select a Date View.
     * If the selected Date is in one of the icons, then select it else unselect all
     */
    private fun selectDateView(layoutNo: Int) {
        when (layoutNo) {
            1 -> setSelectedDateView(
                mBinding.monthLayOne,
                mBinding.tvMonthOne,
                mBinding.tvYearOne
            )
            2 -> setSelectedDateView(
                mBinding.monthLayTwo,
                mBinding.tvMonthTwo,
                mBinding.tvYearTwo
            )
            3 -> setSelectedDateView(
                mBinding.monthLayThree,
                mBinding.tvMonthThree,
                mBinding.tvYearThree
            )
            4 -> setSelectedDateView(
                mBinding.monthLayFour,
                mBinding.tvMonthFour,
                mBinding.tvYearFour
            )
            else -> unselectDateViews()
        }
    }

    /**
     * This method is used to show Selected Date
     */
    private fun setSelectedDateView(monthLayout: View, monthText: TextView, yearText: TextView) {
        //Set color to layout
        setAllView(
            R.drawable.cal_back_select,
            R.drawable.cal_back_unselect,
            monthLayout
        )
        //Set color to layout text
        setAllText(R.color.white, R.color.black, monthText, yearText)
    }

    /**
     * Method to unselect Date Views Icon
     */
    private fun unselectDateViews() {
        setCalendarView(
            R.drawable.cal_back_unselect,
            mBinding.monthLayOne,
            mBinding.monthLayTwo,
            mBinding.monthLayThree,
            mBinding.monthLayFour
        )

        setCalText(
            R.color.black,
            mBinding.tvMonthOne, mBinding.tvYearOne,
            mBinding.tvMonthTwo, mBinding.tvYearTwo,
            mBinding.tvMonthThree, mBinding.tvYearThree,
            mBinding.tvMonthFour, mBinding.tvYearFour
        )
    }

    private fun setAllView(selectedColor: Int, unSelectedColor: Int, vararg views: View) {
        //First unselect all views
        setCalendarView(
            unSelectedColor,
            mBinding.monthLayOne,
            mBinding.monthLayTwo,
            mBinding.monthLayThree,
            mBinding.monthLayFour
        )
        // Then select selected view
        setCalendarView(selectedColor, *views)
    }

    private fun setAllText(selecteColor: Int, unselectCol: Int, vararg txtvws: TextView) {
        setCalText(
            unselectCol,
            mBinding.tvMonthOne, mBinding.tvYearOne,
            mBinding.tvMonthTwo, mBinding.tvYearTwo,
            mBinding.tvMonthThree, mBinding.tvYearThree,
            mBinding.tvMonthFour, mBinding.tvYearFour
        )
        setCalText(selecteColor, *txtvws)
    }

    private fun setCalendarView(res: Int, vararg views: View) {
        for (view in views) {
            view.setBackgroundResource(res)
        }
    }

    private fun setCalText(colorCode: Int, vararg txtvws: TextView) {
        for (tv in txtvws) {
            tv.setTextColor(ContextCompat.getColor(getBaseActivity(), colorCode))
        }
    }
}

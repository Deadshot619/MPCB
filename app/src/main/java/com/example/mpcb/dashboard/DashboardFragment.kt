package com.example.mpcb.dashboard


import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDashboardBinding
import com.example.mpcb.network.response.DashboardDataResponse
import com.example.mpcb.network.response.LoginResponse
import com.example.mpcb.network.response.Users
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.dialog.MonthYearPickerDialog
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.example.mpcb.utils.showMessage
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar.*

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    DashboardNavigator, DatePickerDialog.OnDateSetListener {

    /**
     * These variables will be used to get user Data from Shared Pref
     */
    private val userModel by lazy {
        val user = PreferencesHelper.getPreferences(Constants.USER, "").toString()
        Gson().fromJson(user, LoginResponse::class.java)
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
        mViewModel.getDashboardData("$year-$month-$day")
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
        mBinding.toolbarLayout.run {
            imgCalendar.visibility = View.GONE
        }

        //Check if the user is a SubOrdinate User
        //If the user is subordinate user Show the dropdown & get the UserList from Api
        if (userModel.hasSubbordinateOfficers == 1){
            mBinding.toolbarLayout.spinnerUserList.visibility = View.VISIBLE

            mViewModel.userSpinnerData.observe(this, Observer {
                it?.let {
                    setSpinnerData(it)
                }
            })

            //get User List Data
            mViewModel.getUserListData()
        }

        val calendar = getInstance()

        //Check if Year & Month is set in DatePickerDialog
        val fromDate = if (MonthYearPickerDialog.yearDashboard >= 0 && MonthYearPickerDialog.monthDashboard >= 0)
           MonthYearPickerDialog.yearDashboard.toString() + "-" + (MonthYearPickerDialog.monthDashboard ).toString() + "-" + calendar.getActualMinimum(
                DAY_OF_MONTH
            ).toString()
        else
            calendar.get(YEAR).toString() + "-" + (calendar.get(MONTH) + 1).toString() + "-" + calendar.getActualMinimum(
                DAY_OF_MONTH
            ).toString()

        mViewModel.getDashboardData(fromDate)

        setListeners()

        setDateView()
    }

    //Set data to spinner
    private fun setSpinnerData(it: List<Users>) {
        //create spinnerArray that will hold data from Api
        val spinnerArray = ArrayList<String>()
        for (element in it)
            spinnerArray.add(element.userName)

        //Create a adapter that will be used to set in Spinner
        val adapter = ArrayAdapter<String>(
            context, android.R.layout.simple_spinner_item, spinnerArray
        )

        //Set dropdown View Resource
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Set adapter to spinner
        mBinding.toolbarLayout.spinnerUserList.adapter = adapter
    }

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

    private fun setListeners() {
        val calendar = getInstance()
        val currentTime = calendar.time
        val monthFormat = SimpleDateFormat("MMM")

        mBinding.monthLayOne.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayOne
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthOne, mBinding.tvYearOne)
            calendar.time = currentTime
            val date =
                calendar.get(YEAR).toString() + "-" + (calendar.get(MONTH) + 1).toString() + "-" +
                        calendar.getActualMinimum(DAY_OF_MONTH)
            mViewModel.getDashboardData(date)
        }
        mBinding.monthLayTwo.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayTwo
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthTwo, mBinding.tvYearTwo)
            calendar.time = currentTime
            calendar.add(MONTH, -1)
            val date =
                calendar.get(YEAR).toString() + "-" + (calendar.get(MONTH) + 1).toString() + "-" +
                        calendar.getActualMinimum(DAY_OF_MONTH)
            mViewModel.getDashboardData(date)
        }
        mBinding.monthLayThree.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayThree
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthThree, mBinding.tvYearThree)
            calendar.time = currentTime
            calendar.add(MONTH, -2)
            val date =
                calendar.get(YEAR).toString() + "-" + (calendar.get(MONTH) + 1).toString() + "-" +
                        calendar.getActualMinimum(DAY_OF_MONTH)
            mViewModel.getDashboardData(date)
        }
        mBinding.monthLayFour.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayFour
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthFour, mBinding.tvYearFour)
            calendar.time = currentTime
            calendar.add(MONTH, -3)
            val date =
                calendar.get(YEAR).toString() + "-" + (calendar.get(MONTH) + 1).toString() + "-" +
                        calendar.getActualMinimum(DAY_OF_MONTH)
            mViewModel.getDashboardData(date)
        }
        mBinding.calPickerLay.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(this)
            pd.show(fragmentManager!!, "MonthYearPickerDialog")
        }
    }

    private fun showCalendarDialog() {
        val calendar = getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.e("Date", "" + year + " " + (month + 1) + " " + dayOfMonth)
                    mViewModel.getDashboardData("$year-${month + 1}-$dayOfMonth")
                },
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH)
            )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun setAllView(selectedColor: Int, unSelectedColor: Int, vararg views: View) {
        setCalendarView(
            unSelectedColor,
            mBinding.monthLayOne,
            mBinding.monthLayTwo,
            mBinding.monthLayThree,
            mBinding.monthLayFour
        )
        setCalendarView(selectedColor, *views)
    }

    private fun setAllText(selecteColor: Int, unselectCol: Int, vararg txtvws: TextView) {
        setCalText(
            unselectCol, mBinding.tvMonthOne, mBinding.tvYearOne,
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

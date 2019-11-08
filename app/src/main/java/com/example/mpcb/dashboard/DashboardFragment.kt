package com.example.mpcb.dashboard


import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentDashboardBinding
import com.example.mpcb.utils.dialog.MonthYearPickerDialog
import com.example.mpcb.utils.showMessage
import java.util.*


class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>(),
    DashboardNavigator, DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calender = Calendar.getInstance()
        val day = calender.getActualMinimum(Calendar.DAY_OF_MONTH)
        mViewModel.getDashboardData("$year-${month + 1}-$day")
    }

    override fun getLayoutId() = R.layout.fragment_dashboard
    override fun getViewModel() = DashboardViewModel::class.java
    override fun getNavigator() = this@DashboardFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        mBinding.dashboardModel = mViewModel.getDashboardModel()
        setToolbar(mBinding.toolbarLayout, "MPCB")
        mBinding.toolbarLayout.imgCalendar.visibility = View.GONE
        mViewModel.getDashboardData("2019-10-01")

        setListeners()
    }

    private fun setListeners() {
        mBinding.monthLayOne.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayOne
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthOne, mBinding.tvYearOne)
            mViewModel.getDashboardData("2019-10-01")
        }
        mBinding.monthLayTwo.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayTwo
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthTwo, mBinding.tvYearTwo)
            mViewModel.getDashboardData("2019-09-01")
        }
        mBinding.monthLayThree.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayThree
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthThree, mBinding.tvYearThree)
            mViewModel.getDashboardData("2019-08-01")
        }
        mBinding.monthLayFour.setOnClickListener {
            setAllView(
                R.drawable.cal_back_select,
                R.drawable.cal_back_unselect,
                mBinding.monthLayFour
            )
            setAllText(R.color.white, R.color.black, mBinding.tvMonthFour, mBinding.tvYearFour)
            mViewModel.getDashboardData("2019-07-01")
        }
        mBinding.calPickerLay.setOnClickListener {
            val pd = MonthYearPickerDialog()
            pd.setListener(this)
            pd.show(fragmentManager!!, "MonthYearPickerDialog")
        }
    }

    private fun showCalendarDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.e("Date", "" + year + " " + (month + 1) + " " + dayOfMonth)
                    mViewModel.getDashboardData("$year-${month + 1}-$dayOfMonth")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
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

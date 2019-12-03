package com.example.mpcb.task_management

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentAddTaskBinding
import com.example.mpcb.utils.showMessage
import java.util.*

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding, TaskManagementViewModel>()
    , TaskManagementNavigator {

    override fun getLayoutId() = R.layout.fragment_add_task
    override fun getViewModel() = TaskManagementViewModel::class.java
    override fun getNavigator() = this@AddTaskFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Set Toolbar Layout
        setToolbar(
            toolbarBinding = mBinding.toolbarLayout,
            title = getString(R.string.add_task_title),
            showSearchBar = false
        )

        //Toolbar
        mBinding.toolbarLayout.run{
            //Hide Calender icon
            imgCalendar.visibility = View.GONE
            //Show Back Button
            imgBack.visibility = View.VISIBLE
        }

        //Set click Listeners To views
        setClickListeners()
    }

    /**
     * Method to set Click Listeners to views
     */
    private fun setClickListeners() {
        mBinding.run {
            //Set listener to back button in toolbar
            toolbarLayout.imgBack.setOnClickListener {
                activity!!.onBackPressed()
            }

            //Set listener to Date field
            edtReminderDate.setOnClickListener { showDateDialog() }
        }
    }

    private fun showDateDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog =
            DatePickerDialog(
                getBaseActivity(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    Log.e("Date", "" + year + " " + (month + 1) + " " + dayOfMonth)
                    mBinding.edtReminderDate.setText("$year-${month + 1}-$dayOfMonth")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        datePickerDialog.show()
    }
}


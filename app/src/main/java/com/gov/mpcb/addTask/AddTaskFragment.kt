package com.gov.mpcb.addTask

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragmentReport
import com.gov.mpcb.databinding.FragmentAddTaskBinding
import com.gov.mpcb.utils.showMessage
import java.util.*

class AddTaskFragment : BaseFragmentReport<FragmentAddTaskBinding, AddTaskViewModel>()
    , AddTaskNavigator {

    //instance of UserListFragment for dialog
    private lateinit var dialogFragment: UserListDialog

    override fun getLayoutId() = R.layout.fragment_add_task
    override fun getViewModel() = AddTaskViewModel::class.java
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

            //Set listener to User Details field
            edtUserDetails.setOnClickListener { showUsersListDialog() }
        }
    }

    private fun showUsersListDialog() {
        dialogFragment = UserListDialog.newInstance(context = activity!!, mViewModel = mViewModel)
        dialogFragment.show(parentFragmentManager, AddTaskFragment::class.java.simpleName)
        showMessage("Clicked!")
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


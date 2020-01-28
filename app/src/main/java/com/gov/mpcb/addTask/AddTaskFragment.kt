package com.gov.mpcb.addTask

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import com.gov.mpcb.R
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentAddTaskBinding
import com.gov.mpcb.utils.removeFragment
import com.gov.mpcb.utils.showMessage
import java.util.*

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding, AddTaskViewModel>()
    , AddTaskNavigator {

    //instance of UserListFragment for dialog
    private lateinit var dialogFragment: UserListDialog

    override fun getLayoutId() = R.layout.fragment_add_task
    override fun getViewModel() = AddTaskViewModel::class.java
    override fun getNavigator() = this@AddTaskFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}
    override fun showAlert(message: String) = showMessage(message)
    override fun setText(noOfUsers: Int) = setUserDetailsText(noOfUsers)

    override fun onBinding() {
        clearViewModelValues()

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

        //Fetch users data list
        mViewModel.fetchUsersListData()
    }

    /**
     * Method to clear values of ViewModel
     */
    private fun clearViewModelValues(){
        mViewModel.run {
            selectedUsersTemp.clear()
            addCheckedUserToListSet(selectedUsersTemp)
        }
    }

    /**
     * Method to set Click Listeners to views
     */
    private fun setClickListeners() {
        mBinding.run {
            //Set listener to back button in toolbar
            toolbarLayout.imgBack.setOnClickListener {
                removeFragment(this@AddTaskFragment)
                activity!!.onBackPressed()
            }

            //Set listener to Date field
            edtReminderDate.setOnClickListener { showDateDialog() }

            //Set listener to User Details field
            edtUserDetails.setOnClickListener { showUsersListDialog() }
        }
    }

    //Sets the text in User Details
    private fun setUserDetailsText(noOfUsers: Int){
        if (noOfUsers > 0)
            mBinding.edtUserDetails.setText("$noOfUsers users selected")
        else
            mBinding.edtUserDetails.setText("")
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


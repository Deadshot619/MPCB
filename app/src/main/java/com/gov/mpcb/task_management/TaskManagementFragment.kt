package com.gov.mpcb.task_management


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.mpcb.R
import com.gov.mpcb.addTask.AddTaskFragment
import com.gov.mpcb.base.BaseFragment
import com.gov.mpcb.databinding.FragmentTaskMngtBinding
import com.gov.mpcb.network.response.TaskDetailsData
import com.gov.mpcb.network.response.TaskDetailsResponse
import com.gov.mpcb.utils.addFragment
import com.gov.mpcb.utils.showMessage


class TaskManagementFragment : BaseFragment<FragmentTaskMngtBinding, TaskManagementViewModel>()
    , TaskManagementNavigator {

    private lateinit var adapter: TaskMngtAdapter

    override fun getLayoutId() = R.layout.fragment_task_mngt
    override fun getViewModel() = TaskManagementViewModel::class.java
    override fun getNavigator() = this@TaskManagementFragment
    override fun onError(message: String) = showMessage(message)

    /**
     * Method to update list of TaskManagement Adapter
     */
    override fun submitRecyclerViewData(taskDetailsResponse: TaskDetailsResponse) {
        adapter.updateList(taskDetailsResponse.data as ArrayList<TaskDetailsData>)
    }

    override fun showAlert(message: String) {
        showMessage(message)
    }

    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(
            mBinding.toolbarLayout,
            getString(R.string.task_mngt_title),
            showSearchBar = true
        )
        mBinding.toolbarLayout.imgCalendar.visibility = View.GONE
        setUpRecyclerView()
        setFloatingActionButton()
    }

    private fun setFloatingActionButton() {
        val bundle = Bundle()
        mBinding.fabTaskManagement.setOnClickListener {
            addFragment(AddTaskFragment(), false, bundle)
        }
    }

    private fun setUpRecyclerView() {
        mBinding.rvTasks.layoutManager = LinearLayoutManager(getBaseActivity())
        adapter = TaskMngtAdapter(getBaseActivity(), mViewModel)
        mBinding.rvTasks.adapter = adapter
        mViewModel.getTaskListData()
    }
}

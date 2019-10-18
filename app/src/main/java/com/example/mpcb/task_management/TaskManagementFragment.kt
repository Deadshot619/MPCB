package com.example.mpcb.task_management


import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentTaskMngtBinding
import com.example.mpcb.utils.showMessage


class TaskManagementFragment : BaseFragment<FragmentTaskMngtBinding, TaskManagementViewModel>()
    , TaskManagementNavigator {

    override fun getLayoutId() = R.layout.fragment_task_mngt
    override fun getViewModel() = TaskManagementViewModel::class.java
    override fun getNavigator() = this@TaskManagementFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, getString(R.string.task_mngt_title))
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mBinding.rvTasks.layoutManager = LinearLayoutManager(getBaseActivity())
        val adapter = TaskMngtAdapter(getBaseActivity(), mViewModel)
        mBinding.rvTasks.adapter = adapter
    }

}

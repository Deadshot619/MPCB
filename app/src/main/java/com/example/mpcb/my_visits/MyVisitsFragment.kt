package com.example.mpcb.my_visits


import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mpcb.R
import com.example.mpcb.base.BaseFragment
import com.example.mpcb.databinding.FragmentMyVisitsBinding
import com.example.mpcb.utils.showMessage


class MyVisitsFragment : BaseFragment<FragmentMyVisitsBinding, MyVisitsViewModel>(), MyVisitsNavigator {

    override fun getLayoutId() = R.layout.fragment_my_visits
    override fun getViewModel() = MyVisitsViewModel::class.java
    override fun getNavigator() = this@MyVisitsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        setToolbar(mBinding.toolbarLayout, getString(R.string.my_visits_title))
        mBinding.rvMyVisits.layoutManager = LinearLayoutManager(getBaseActivity())
        val adapter = MyVisitsAdapter(getBaseActivity(), mViewModel)
        mBinding.rvMyVisits.adapter = adapter
        mViewModel.getVisitList().observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })
        mViewModel.getVisitListData()
    }

}

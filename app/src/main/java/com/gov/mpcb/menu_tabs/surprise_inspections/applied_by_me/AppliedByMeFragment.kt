package com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gov.mpcb.R
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.SurpriseInspectionsViewModel
import com.gov.mpcb.utils.showMessage

/**
 * A simple [Fragment] subclass.
 */
/*class AppliedByMeFragment : BaseFragment<FragmentAppliedByMeBinding, AppliedByMeViewModel>(),
AppliedByMeNavigator{

    override fun getLayoutId() = R.layout.fragment_applied_by_me
    override fun getViewModel() = AppliedByMeViewModel::class.java
    override fun getNavigator() = this@AppliedByMeFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        setUpListeners()
    }

    private fun setUpListeners() {

    }

}*/

class AppliedByMeFragment : Fragment(){

    private lateinit var mBinding : FragmentAppliedByMeBinding
    private lateinit var mViewModel: SurpriseInspectionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_applied_by_me, container,false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = activity?.run {
            ViewModelProvider(this).get(SurpriseInspectionsViewModel::class.java)
        }!!

        mViewModel._viewAppliedLists.observe(viewLifecycleOwner, Observer {
            if (it.data.isNotEmpty())
                showMessage("${it.total_rows}")
            else
                showMessage("${it.total_rows}")
        })
    }

}
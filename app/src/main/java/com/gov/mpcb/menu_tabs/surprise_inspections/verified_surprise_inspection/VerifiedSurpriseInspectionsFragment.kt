package com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gov.mpcb.R
import com.gov.mpcb.databinding.FragmentAppliedByMeBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.SurpriseInspectionsViewModel
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeAdapter
import com.gov.mpcb.network.response.ViewAppliedListData
import com.gov.mpcb.network.response.ViewAppliedListResponse

/**
 * A simple [Fragment] subclass.
 */
/*class VerifiedSurpriseInspectionsFragment : BaseFragment<FragmentVerifiedSurpriseInspectionsBinding, VerifiedSurpriseInspectionsViewModel>(),
    VerifiedSurpriseInspectionsNavigator {

    override fun getLayoutId() = R.layout.fragment_verified_surprise_inspections
    override fun getViewModel() = VerifiedSurpriseInspectionsViewModel::class.java
    override fun getNavigator() = this@VerifiedSurpriseInspectionsFragment
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {

        setUpListeners()
    }

    private fun setUpListeners() {

    }


}*/

class VerifiedSurpriseInspectionsFragment : Fragment() {

    private lateinit var mBinding: FragmentAppliedByMeBinding
    private lateinit var mViewModel: SurpriseInspectionsViewModel
    private lateinit var adapter : AppliedByMeAdapter
    private var viewAppliedListData: List<ViewAppliedListData>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_applied_by_me, container, false)

        viewAppliedListData = arguments?.getParcelable<ViewAppliedListResponse>("data")?.data?.run { this }

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = activity?.run {
            ViewModelProvider(this).get(SurpriseInspectionsViewModel::class.java)
        }!!

        setUpRecyclerView(mBinding.rvListings)

//        mViewModel._viewAppliedLists.observe(viewLifecycleOwner, Observer {
//            if (it.data.isNotEmpty()) {
//                adapter.submitList(viewAppliedListData)
//            } else
//                showMessage("${it.total_rows}")
//        })
    }

    private fun filterData(list: List<ViewAppliedListData>): List<ViewAppliedListData>{
        return list.filter { it.is_approved_by_hod == 1 }
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        adapter = AppliedByMeAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        viewAppliedListData?.let {
            adapter.submitList(filterData(it))
        }

//        recyclerView.adapter!!.notifyDataSetChanged()
//        recyclerView.setHasFixedSize(true)
    }

}
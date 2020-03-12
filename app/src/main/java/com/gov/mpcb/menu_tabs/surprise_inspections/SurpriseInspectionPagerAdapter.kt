package com.gov.mpcb.menu_tabs.surprise_inspections

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeFragment
import com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection.VerifiedSurpriseInspectionsFragment
import com.gov.mpcb.network.response.ViewAppliedListResponse

class SurpriseInspectionPagerAdapter(activity: SurpriseInspectionActivity, list: ViewAppliedListResponse = ViewAppliedListResponse()) :
    FragmentStateAdapter(activity) {

    private var mList = list

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> AppliedByMeFragment().apply {
                arguments = Bundle().apply {  putParcelable("data", mList)   }
            }
            1 -> VerifiedSurpriseInspectionsFragment().apply {
                arguments = Bundle().apply {  putParcelable("data", mList)   }
            }
            else -> AppliedByMeFragment().apply {
                arguments = Bundle().apply {  putParcelable("data", mList)   }
            }
        }
    }


    fun refreshData(list: ViewAppliedListResponse) {
        mList = list
        notifyDataSetChanged()
    }
}
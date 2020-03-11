package com.gov.mpcb.menu_tabs.surprise_inspections

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeFragment
import com.gov.mpcb.menu_tabs.surprise_inspections.verified_surprise_inspection.VerifiedSurpriseInspectionsFragment

class SurpriseInspectionPagerAdapter(activity: SurpriseInspectionActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position){
//            0 -> AppliedByMeFragment()
            1 -> VerifiedSurpriseInspectionsFragment()
            else -> AppliedByMeFragment()
        }
    }
}
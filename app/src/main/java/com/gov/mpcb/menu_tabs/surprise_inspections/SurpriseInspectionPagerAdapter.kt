package com.gov.mpcb.menu_tabs.surprise_inspections

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gov.mpcb.menu_tabs.surprise_inspections.applied_by_me.AppliedByMeFragment
import com.gov.mpcb.network.response.ViewAppliedListResponse
import com.gov.mpcb.utils.constants.Constants

class SurpriseInspectionPagerAdapter(
    activity: SurpriseInspectionActivity,
    list: ViewAppliedListResponse = ViewAppliedListResponse()
) :
    FragmentStateAdapter(activity) {

    private var mList = list

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AppliedByMeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.SI_DATA, mList)
                    putBoolean(Constants.ADDED_BY_ME, true)
                }
            }
            1 -> AppliedByMeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.SI_DATA, mList)
                    putBoolean(Constants.ADDED_BY_ME, false)
                }
            }
            else -> AppliedByMeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.SI_DATA, mList)
                    putBoolean(Constants.ADDED_BY_ME, true)
                }
            }
        }
    }


    fun refreshData(list: ViewAppliedListResponse) {
        mList = list
        notifyDataSetChanged()
    }
}
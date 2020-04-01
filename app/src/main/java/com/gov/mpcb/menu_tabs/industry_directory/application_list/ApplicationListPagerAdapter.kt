package com.gov.mpcb.menu_tabs.surprise_inspections

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gov.mpcb.menu_tabs.industry_directory.application_list.ApplicationListFragment
import com.gov.mpcb.menu_tabs.industry_directory.consent.IdConsentFragment
import com.gov.mpcb.utils.constants.Constants

class ApplicationListPagerAdapter(
    fragment: ApplicationListFragment,
    val industryId: Int
) :
    FragmentStateAdapter(fragment) {

    val fragmentList = listOf<Fragment>(IdConsentFragment())

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].apply {
            arguments = Bundle().apply {
                putInt(Constants.INDUSTRY_ID, industryId)
            }
        }
    }
}

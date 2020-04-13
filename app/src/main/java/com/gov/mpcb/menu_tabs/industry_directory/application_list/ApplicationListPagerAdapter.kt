package com.gov.mpcb.menu_tabs.surprise_inspections

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gov.mpcb.menu_tabs.industry_directory.application_list.ApplicationListFragment
import com.gov.mpcb.utils.constants.Constants

class ApplicationListPagerAdapter(
    fragment: ApplicationListFragment,
    val industryId: Int,
    val pagerData: List<Fragment>
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = pagerData.size

    override fun createFragment(position: Int): Fragment {
        return pagerData[position].apply {
            arguments = Bundle().apply {
                putInt(Constants.INDUSTRY_ID, industryId)
            }
        }
    }
}

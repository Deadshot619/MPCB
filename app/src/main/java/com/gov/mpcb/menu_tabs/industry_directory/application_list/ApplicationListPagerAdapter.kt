package com.gov.mpcb.menu_tabs.surprise_inspections

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gov.mpcb.menu_tabs.industry_directory.application_list.ApplicationListFragment
import com.gov.mpcb.menu_tabs.industry_directory.consent.IdConsentFragment

class ApplicationListPagerAdapter(
    fragment: ApplicationListFragment
) :
    FragmentStateAdapter(fragment) {

    val fragmentList = listOf<Fragment>()

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IdConsentFragment()
            else -> IdConsentFragment()
            }
        }
    }

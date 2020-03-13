package com.gov.mpcb.menu_tabs.surprise_inspections.add_surprise_inspection

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivityAddSurpriseInspectionBinding
import com.gov.mpcb.menu_tabs.surprise_inspections.industry_list.IndustryListFragment
import com.gov.mpcb.utils.showMessage

class AddSurpriseInspectionActivity : BaseActivity<ActivityAddSurpriseInspectionBinding, AddSurpriseInspectionViewModel>() ,
AddSurpriseInspectionNavigator{

    override fun getLayoutId() = R.layout.activity_add_surprise_inspection
    override fun getViewModel() = AddSurpriseInspectionViewModel::class.java
    override fun getNavigator() = this@AddSurpriseInspectionActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        //Add Industry List Fragment
        addFragment(
            fragment = IndustryListFragment(),
            addToBackstack = true,
            bundle = null
        )
    }


}

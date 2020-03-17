package com.gov.mpcb.menu_tabs.surprise_inspections.apply_for_surprise_inspection

import android.app.Activity
import com.gov.mpcb.base.BaseNavigator

interface ApplyForSurpriseInspectionNavigator : BaseNavigator {
    fun showToast(msg: String)
    fun openActivity(activity: Activity)
}
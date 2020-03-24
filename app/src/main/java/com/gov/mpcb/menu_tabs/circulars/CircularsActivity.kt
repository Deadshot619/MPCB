package com.gov.mpcb.menu_tabs.circulars

import com.gov.mpcb.R
import com.gov.mpcb.base.BaseActivity
import com.gov.mpcb.databinding.ActivityCircularsBinding
import com.gov.mpcb.menu_tabs.circulars.show_circulars.CircularsFragment
import com.gov.mpcb.utils.showMessage

class CircularsActivity : BaseActivity<ActivityCircularsBinding, CircularsActivityViewModel>(),
    CircularsActivityNavigator {
    override fun getLayoutId() = R.layout.activity_circulars
    override fun getViewModel() = CircularsActivityViewModel::class.java
    override fun getNavigator() = this@CircularsActivity
    override fun onError(message: String) = showMessage(message)
    override fun onInternetError() {}

    override fun onBinding() {
        addFragment(CircularsFragment(), false, null)
    }
}

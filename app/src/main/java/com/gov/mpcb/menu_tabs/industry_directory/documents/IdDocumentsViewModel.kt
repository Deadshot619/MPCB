package com.gov.mpcb.menu_tabs.industry_directory.documents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gov.mpcb.base.BaseViewModel
import com.gov.mpcb.utils.LoadingStatus

class IdDocumentsViewModel : BaseViewModel<IdDocumentsNavigator>() {

    /**
     * This variable holds the data which will be used to show/hide ProgresBar
     */
    private val progressStatus = MutableLiveData<LoadingStatus>(LoadingStatus.DONE)
    val _progressStatus: LiveData<LoadingStatus>
        get() = progressStatus

}

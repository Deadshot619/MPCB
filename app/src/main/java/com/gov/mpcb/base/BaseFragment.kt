package com.gov.mpcb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gov.mpcb.databinding.ToolbarBinding

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewModel = ViewModelProvider(this@BaseFragment).get(getViewModel())
        mViewModel.setNavigator(getNavigator())
//        val reportData = PreferencesHelper.getStringPreference(Constants.REPORT_KEY, "")

//        val visitId = getDataFromArguments(this, Constants.VISIT_REPORT_ID)
//        report =
////            if (reportData!!.isNotEmpty()) Gson().fromJson(reportData, ReportRequest::class.java)
//            if (getReportData(visitId) != null) getReportData(visitId)!!
//            else ReportRequest()

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDialog()
        onBinding()
    }

    private fun createDialog() {
        val dialog = LoadingDialog(getBaseActivity())
        mViewModel.getVisibility().observe(viewLifecycleOwner, Observer { show ->
            dialog.run {
                if (show) show() else hide()
            }
        })
        mViewModel.getMessage().observe(viewLifecycleOwner, Observer {
            dialog.setMessage(it)
        })
    }

    protected fun getBaseActivity() = activity as BaseActivity<*, *>

    protected fun setToolbar(
        toolbarBinding: ToolbarBinding,
        title: String,
        showSearchBar: Boolean = false,
        showCalendar: Boolean = false,
        showBackButton: Boolean = false
    ) {
        toolbarBinding.run {

            //Toolbar title
            txtToolbarTitle.text = title

            //if true, then Display search icon & add click listeners to it
            if (showSearchBar) {
                //Show Search Icon
                imgSearch.visibility = View.VISIBLE

                //Search icon click listener
                imgSearch.setOnClickListener {
                    //Hide main toolbar
                    mainToolbar.visibility = View.GONE
                    //show searchbar
                    searchbarLayout.visibility = View.VISIBLE

                    //set focus on search bar programmatically
                    searchBar.isIconified = false
                }

                //SearchBar click listener
                searchBar.setOnCloseListener {
                    //show main toolbar
                    mainToolbar.visibility = View.VISIBLE
                    //hide searchbar & clear focus form it
                    searchBar.clearFocus()
                    searchbarLayout.visibility = View.GONE
                    true
                }
            }

            //If true, show Calendar, else hide it
            if (showCalendar) {
                imgCalendar.visibility = View.VISIBLE
            } else {
                imgCalendar.visibility = View.GONE
            }

            //If true, show Back Button, else hide it
            if (showBackButton) {
                imgBack.visibility = View.VISIBLE
            } else {
                imgBack.visibility = View.GONE
            }
        }
    }

    /**
     * This method is used to get data from Fragment arguments
     */
    protected fun getDataFromArguments(context: Fragment, key: String): String {
        return context.arguments?.getString(key)!!
        //Not a good way to do, due to tight coupling of fragment to activity
//        return ReportsPageActivity().visitReportId
    }
}


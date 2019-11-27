package com.example.mpcb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mpcb.databinding.ToolbarBinding
import com.example.mpcb.network.request.ReportRequest
import com.example.mpcb.reports.additional_info.AdditionalInfoFragment
import com.example.mpcb.reports.air_pollution.AirFragment
import com.example.mpcb.reports.bank_guarantee_details.BGDFragment
import com.example.mpcb.reports.disposal.DisposalFragment
import com.example.mpcb.reports.electric_meter_details.ElectricFragment
import com.example.mpcb.reports.hazardous_waste_management.HazardousFragment
import com.example.mpcb.reports.industry.IndustryReportFragment
import com.example.mpcb.reports.last_jvs_details.LastJVSFragment
import com.example.mpcb.reports.non_hazardous_waste_management.NonHazardousFragment
import com.example.mpcb.reports.oms_ambient_air.OMSAmbientAirFragment
import com.example.mpcb.reports.oms_stack.OMSStackFragment
import com.example.mpcb.reports.oms_water.OMSWaterFragment
import com.example.mpcb.reports.previous_legal_actions.PreviousLegalFragment
import com.example.mpcb.reports.production.ProductionFragment
import com.example.mpcb.reports.statutory_submissions.StatutoryFragment
import com.example.mpcb.reports.treatment.TreatmentFragment
import com.example.mpcb.reports.tree_plantation.TreePlantationFragment
import com.example.mpcb.reports.water_and_waste_water.WaterFragment
import com.example.mpcb.utils.constants.Constants
import com.example.mpcb.utils.shared_prefrence.PreferencesHelper
import com.google.gson.Gson

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V
    protected lateinit var report: ReportRequest

    /**
     * This method is to be implemented in the child classes.
     * This method should retrieve & set data to views in Reports.
     */
    protected open fun setDataToViews() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewModel = ViewModelProvider(getBaseActivity()).get(getViewModel())
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

    /**
     * This method is used to set data to [report] variable
     */
    protected fun setReportVariableData(visitReportId: String) {
        report = getReportData(visitReportId) ?: ReportRequest()
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

    protected fun setToolbar(toolbarBinding: ToolbarBinding, title: String, showSearchBar: Boolean = false) {
        toolbarBinding.txtToolbarTitle.text = title

        //if true, then Display search icon & add click listeners to it
        if (showSearchBar){
            //Show Search Icon
            toolbarBinding.imgSearch.visibility = View.VISIBLE

            //Search icon click listener
            toolbarBinding.imgSearch.setOnClickListener {
                //Hide main toolbar
                toolbarBinding.mainToolbar.visibility = View.GONE
                //show searchbar
                toolbarBinding.searchbarLayout.visibility = View.VISIBLE

                //set focus on search bar programmatically
                toolbarBinding.searchBar.isIconified = false

            }

            //SearchBar click listener
            toolbarBinding.searchBar.setOnCloseListener {
                //show main toolbar
                toolbarBinding.mainToolbar.visibility = View.VISIBLE
                //hide searchbar & clear focus form it
                toolbarBinding.searchBar.clearFocus()
                toolbarBinding.searchbarLayout.visibility = View.GONE
                true
            }
        }
    }

    protected fun addReportFragment(reportKey: Int, bundle: Bundle? = null) {
        val fragment = when (reportKey) {
            Constants.REPORT_1 -> IndustryReportFragment() //v
            Constants.REPORT_2 -> ProductionFragment()// listing //v
            Constants.REPORT_3 -> TreatmentFragment()//v
            Constants.REPORT_4 -> WaterFragment()//v
            Constants.REPORT_5 -> DisposalFragment()//v
            Constants.REPORT_6 -> OMSWaterFragment() //v
            Constants.REPORT_7 -> ElectricFragment() //v
            Constants.REPORT_8 -> LastJVSFragment() // listing
            Constants.REPORT_9 -> AirFragment() // listing //v
            Constants.REPORT_10 -> OMSStackFragment()//v
            Constants.REPORT_11 -> OMSAmbientAirFragment()// listing //v
            Constants.REPORT_12 -> HazardousFragment()// listing//v
            Constants.REPORT_13 -> NonHazardousFragment()// listing //v
            Constants.REPORT_14 -> TreePlantationFragment()//v
            Constants.REPORT_15 -> StatutoryFragment() //v
            Constants.REPORT_16 -> PreviousLegalFragment() //v
            Constants.REPORT_17 -> BGDFragment()// listing //v
            Constants.REPORT_18 -> AdditionalInfoFragment() //v
            else -> Fragment()
        }

        getBaseActivity().addReportFragment(fragment, true, bundle)
    }

    /**
     * This method is used to get data from Fragment arguments
     */
    protected fun getDataFromArguments(context: Fragment, key: String): String {
        return context.arguments?.getString(key)!!
        //Not a good way to do, due to tight coupling of fragment to activity
//        return ReportsPageActivity().visitReportId
    }


    /**
     * This method is used to save the reports data & its status in the
     * shared preference.
     * @param reportKey 0 by default. Specifies the Report
     * @param reportStatus false by default. Indicates whether the report status is completed
     *                      or not
     */
    protected fun saveReportData(
        reportNo: String,
        reportKey: Int = 0,
        reportStatus: Boolean = false
    ) {
//        PreferencesHelper.setPreferences(Constants.REPORT_KEY, Gson().toJson(report))

        //Reports are saved according to their Report No.
        PreferencesHelper.setPreferences(
            key = reportNo,
            value = Gson().toJson(report)
        )

        //saves the status of current report
        PreferencesHelper.setReportFlagStatus(
            reportNo = reportNo,
            reportKey = reportKey,
            reportStatus = reportStatus
        )
    }

    /**
     * This method is used to retrieve Reports data from Shared Prefs
     *
     * @return [ReportRequest] returns an object of ReportRequest
     */
    protected fun getReportData(reportNo: String): ReportRequest? {
//        val reports = PreferencesHelper.getPreferences(Constants.REPORT_KEY, "")
        val reports = PreferencesHelper.getPreferences(reportNo, "")
        return Gson().fromJson(reports as String, ReportRequest::class.java)
    }
}


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
import com.google.gson.Gson
import com.gov.mpcb.databinding.ButtonSaveNextLayoutBinding
import com.gov.mpcb.databinding.ToolbarBinding
import com.gov.mpcb.network.request.ReportRequest
import com.gov.mpcb.reports.additional_info.AdditionalInfoFragment
import com.gov.mpcb.reports.air_pollution.AirFragment
import com.gov.mpcb.reports.bank_guarantee_details.BGDFragment
import com.gov.mpcb.reports.disposal.DisposalFragment
import com.gov.mpcb.reports.electric_meter_details.ElectricFragment
import com.gov.mpcb.reports.hazardous_waste_management.HazardousFragment
import com.gov.mpcb.reports.industry.IndustryReportFragment
import com.gov.mpcb.reports.last_jvs_details.LastJVSFragment
import com.gov.mpcb.reports.non_hazardous_waste_management.NonHazardousFragment
import com.gov.mpcb.reports.oms_ambient_air.OMSAmbientAirFragment
import com.gov.mpcb.reports.oms_stack.OMSStackFragment
import com.gov.mpcb.reports.oms_water.OMSWaterFragment
import com.gov.mpcb.reports.previous_legal_actions.PreviousLegalFragment
import com.gov.mpcb.reports.production.ProductionFragment
import com.gov.mpcb.reports.statutory_submissions.StatutoryFragment
import com.gov.mpcb.reports.treatment.TreatmentFragment
import com.gov.mpcb.reports.tree_plantation.TreePlantationFragment
import com.gov.mpcb.reports.water_and_waste_water.WaterFragment
import com.gov.mpcb.utils.constants.Constants
import com.gov.mpcb.utils.constants.Constants.Companion.disableEnableControls
import com.gov.mpcb.utils.shared_prefrence.PreferencesHelper

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    UICallbacks<V> {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: V
    protected lateinit var report: ReportRequest

    /**
     * This variable will be used to check if Visit Status if VISITED or not.
     * Mostly used in Reports to then enable/disable views
     */
    protected val visitStatus: Boolean
        get() = PreferencesHelper.getBooleanPreference(Constants.VISIT_STATUS)

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

    protected fun setToolbar(
        toolbarBinding: ToolbarBinding,
        title: String,
        showSearchBar: Boolean = false
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
        }
    }

    protected fun addReportFragment(
        reportKey: Int,
        bundle: Bundle? = null,
        addToBackStack: Boolean = true
    ) {
        Constants.run {
            val fragment = when (reportKey) {
                REPORT_1 -> IndustryReportFragment() //v
                REPORT_2 -> ProductionFragment()// listing //v
                REPORT_3 -> WaterFragment()//v
                REPORT_4 -> TreatmentFragment()//v
                REPORT_5 -> DisposalFragment()//v
                REPORT_6 -> OMSWaterFragment() //v
                REPORT_7 -> ElectricFragment() //v
                REPORT_8 -> LastJVSFragment() // listing
                REPORT_9 -> AirFragment() // listing //v
                REPORT_10 -> OMSStackFragment()//v
                REPORT_11 -> OMSAmbientAirFragment()// listing //v
                REPORT_12 -> HazardousFragment()// listing//v
                REPORT_13 -> NonHazardousFragment()// listing //v
                REPORT_14 -> TreePlantationFragment()//v
                REPORT_15 -> StatutoryFragment() //v
                REPORT_16 -> PreviousLegalFragment() //v
                REPORT_17 -> BGDFragment()// listing //v
                REPORT_18 -> AdditionalInfoFragment() //v
                else -> Fragment()
            }

            getBaseActivity().addReportFragment(fragment, addToBackStack, bundle)
        }
    }

    /**
     * This method will be used in onClick of Submit/Next button in report fragments to
     * go to next fragment.
     */
    protected fun addReportFragmentLocal(constantReportValue: Int, visitReportId: String) {
        //Put the Visit Report ID in bundle to share to Fragments
        val bundle = Bundle()
        bundle.putString(Constants.VISIT_REPORT_ID, visitReportId)
        addReportFragment(constantReportValue, bundle)
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

    /**
     * This method Enables/Disables the views in  a viewGroup depending on visitStatus
     */
    protected fun disableViews(viewGroup: ViewGroup) {
        //If true, disable all controls!
        if (visitStatus)
            disableEnableControls(false, viewGroup)
    }

    /**
     * This method shows or hides the Next button depending on Visit Status.
     */
    protected fun showNextAndPreviousButton(
        btnSaveNextLayoutBinding: ButtonSaveNextLayoutBinding,
        showPreviousButton: Boolean = true
    ) {
        //If true show 'Next & Previous' button & hide 'Save' button
        if (visitStatus) {
            btnSaveNextLayoutBinding.run {
                btnNext.visibility = View.VISIBLE
                btnSubmit.visibility = View.GONE
            }
        } else {
            btnSaveNextLayoutBinding.run {
                btnNext.visibility = View.GONE
                btnSubmit.visibility = View.VISIBLE
            }
        }

        //if true, Show previous button & set click listener to it
        if (showPreviousButton)
            btnSaveNextLayoutBinding.btnPrevious.run {
                visibility = View.VISIBLE
                setOnClickListener {
                    activity?.onBackPressed()
                }
            }
    }
}


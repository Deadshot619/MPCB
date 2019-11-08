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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewModel = ViewModelProvider(getBaseActivity()).get(getViewModel())
        mViewModel.setNavigator(getNavigator())
        val reportData = PreferencesHelper.getStringPreference(Constants.REPORT_KEY, "")
        report =
            if (reportData!!.isNotEmpty()) Gson().fromJson(reportData, ReportRequest::class.java)
            else ReportRequest()

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

    protected fun setToolbar(toolbarBinding: ToolbarBinding, title: String) {
        toolbarBinding.txtToolbarTitle.text = title
    }

    protected fun addReportFragment(reportKey: Int) {
        val fragment = when (reportKey) {
            Constants.REPORT_1 -> IndustryReportFragment()
            Constants.REPORT_2 -> ProductionFragment()// listing
            Constants.REPORT_3 -> TreatmentFragment()
            Constants.REPORT_4 -> WaterFragment()
            Constants.REPORT_5 -> DisposalFragment()
            Constants.REPORT_6 -> OMSWaterFragment()
            Constants.REPORT_7 -> ElectricFragment()
            Constants.REPORT_8 -> LastJVSFragment() // listing
            Constants.REPORT_9 -> AirFragment() // listing
            Constants.REPORT_10 -> OMSStackFragment()
            Constants.REPORT_11 -> OMSAmbientAirFragment()// listing
            Constants.REPORT_12 -> HazardousFragment()// listing
            Constants.REPORT_13 -> NonHazardousFragment()// listing
            Constants.REPORT_14 -> TreePlantationFragment()
            Constants.REPORT_15 -> StatutoryFragment()
            Constants.REPORT_16 -> PreviousLegalFragment()
            Constants.REPORT_17 -> BGDFragment()// listing
            Constants.REPORT_18 -> AdditionalInfoFragment()
            else -> Fragment()
        }

        getBaseActivity().addReportFragment(fragment, true)
    }

    protected fun saveReportData() {
        PreferencesHelper.setPreferences(Constants.REPORT_KEY, Gson().toJson(report))
    }
}

